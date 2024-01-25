package nex.vts.backend.repoImpl;

import nex.vts.backend.exceptions.AppCommonException;
import nex.vts.backend.models.responses.OverSpeedData;
import nex.vts.backend.models.responses.VehiclePositionReportData;
import nex.vts.backend.repositories.OverSpeedRepo;
import nex.vts.backend.repositories.VehiclePositionRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.TransientDataAccessException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class OverSpeedImpl implements OverSpeedRepo {
    private final short API_VERSION = 1;
    @Autowired
    Environment environment;
    @Autowired
    private PlatformTransactionManager transactionManager;
    List<Map<String, Object>> results;

    private final Logger logger = LoggerFactory.getLogger(OverSpeedImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Optional<ArrayList<OverSpeedData>> getOverSpeedInfo(String p_alert_type, String p_report_type, int p_profile_type, Long p_profile_id, Long p_profile_p_id, int p_all_vehicle_flag, Long p_vehicle_id, String p_date_from, String p_date_to, int deviceType) {

        String shcemaName = environment.getProperty("application.profiles.shcemaName");

        Optional<ArrayList<OverSpeedData>> datalList;


        String callProcedureSql = "CALL " + shcemaName + "GENERATE_ALERT_REPORT_DATA('SPEED', 'D',"+p_profile_type+","+p_profile_id+","
                +p_profile_p_id+","+p_all_vehicle_flag+","+p_vehicle_id+",'"+p_date_from+"','"+p_date_to+"')";
        System.out.println(callProcedureSql);
      //  String callProcedureSql = "CALL " + shcemaName + "GENERATE_ALERT_REPORT_DATA(?, ?,?,?,?,?,?,?,?)";

        String sql = null;

        if (p_profile_type == 1) {
            sql = "SELECT * from \n" +
                    "(\n" +
                    "    select \n" +
                    "      VEHICLEID, \n" +
                    "      " + shcemaName + "GET_VEHICLE_NAME(0, VEHICLEID) VEHICLE_NAME, \n" +
                    "      LOCATION, \n" +
                    "      LAT, \n" +
                    "      LON, \n" +
                    "      ALERT_TYPE, \n" +
                    "      BREAK_TIME, \n" +
                    "      SPEED, \n" +
                    "      row_number() over (order by VEHICLEID DESC) rowno \n" +
                    "    FROM " + shcemaName + "NEX_ALL_ALERT_REPORT_DATA \n" +
                    "    WHERE BREAK_TIME BETWEEN '" + p_date_from + "' AND '" + p_date_to + "'\n" +
                    ") \n" +
                    "order by rowno";
        } else {
            sql = "select VEHICLEID,VEHICLE_NAME,ALERT_TYPE,TOTAL_ALERT,row_number() over (order by VEHICLE_NAME, ALERT_TYPE) rowno\n" +
                    "        from\n" +
                    "        (\n" +
                    "       select VEHICLEID," + shcemaName + "GET_VEHICLE_NAME(0, VEHICLEID) VEHICLE_NAME,ALERT_TYPE,TOTAL_ALERT\n" +
                    "       from\n" +
                    "       (\n" +
                    "       SELECT VEHICLEID,ALERT_TYPE,COUNT(VEHICLEID) TOTAL_ALERT\n" +
                    "       FROM  " + shcemaName + "NEX_ALL_ALERT_REPORT_DATA\n" +
                    "       WHERE BREAK_TIME BETWEEN " + p_date_from + " AND " + p_date_to + "\n" +
                    "       GROUP BY VEHICLEID, ALERT_TYPE\n" +
                    "       )\n" +
                    "       )\n" +
                    "       order by rowno";
        }


        try {
            // Define transaction attributes
            DefaultTransactionDefinition def = new DefaultTransactionDefinition();
            TransactionStatus status = transactionManager.getTransaction(def);
          //"SPEED", "D",1,14461,0,1,0,'20240125000000','20240125235959'
            /*jdbcTemplate.update(callProcedureSql, "SPEED", "D", p_profile_type, p_profile_id, p_profile_p_id,
                    p_all_vehicle_flag,p_vehicle_id,p_date_from, p_date_to);*/
            jdbcTemplate.update(callProcedureSql);
            ArrayList<OverSpeedData> overSpeedDataList = (ArrayList<OverSpeedData>) jdbcTemplate.query(sql, (rs, rowNum) -> {
                OverSpeedData overSpeedData = new OverSpeedData();
                overSpeedData.setVehicleid(rs.getString("VEHICLEID"));
                return overSpeedData;
            });
            //results = jdbcTemplate.queryForList(sql);
            transactionManager.commit(status);

            datalList = Optional.ofNullable(overSpeedDataList);
            //datalList = Optional.empty();
        } catch (BadSqlGrammarException e) {
            logger.trace("No Data found with userId is {}  Sql Grammar Exception", p_profile_id);
            throw new AppCommonException(4001 + "##Sql Grammar Exception##" + deviceType + "##" + API_VERSION);
        } catch (TransientDataAccessException f) {
            logger.trace("No Data found with userId is {} network or driver issue or db is temporarily unavailable  ", p_profile_id);
            throw new AppCommonException(4002 + "##Network or driver issue or db is temporarily unavailable##" + deviceType + "##" + API_VERSION);
        } catch (CannotGetJdbcConnectionException g) {
            logger.trace("No Data found with userId is {} could not acquire a jdbc connection  ", p_profile_id);
            throw new AppCommonException(4003 + "##A database connection could not be obtained##" + deviceType + "##" + API_VERSION);
        }
        return datalList;


    }
}
