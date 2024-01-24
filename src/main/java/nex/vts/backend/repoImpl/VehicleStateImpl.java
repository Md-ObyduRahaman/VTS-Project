package nex.vts.backend.repoImpl;

import nex.vts.backend.exceptions.AppCommonException;
import nex.vts.backend.models.responses.VehicleStateInfoOra;
import nex.vts.backend.models.vehicle.rowMapper.VehicleStateInfo_RowMapper;
import nex.vts.backend.repositories.VehicleStateRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.TransientDataAccessException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class VehicleStateImpl implements VehicleStateRepo {


    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    Environment environment;
    @Autowired
    private TransactionTemplate transactionTemplate;

    private final Logger logger = LoggerFactory.getLogger(VehicleStateImpl.class);

    @Override
    public Optional<ArrayList<VehicleStateInfoOra>> findVehicleStateInfoInfo(Integer parentProfileId,Integer userType,Integer userId,String SPECIFIC_VEHICLE_ID) {
        logger.debug("Executing query to get client profile by client parentProfileId: {}", parentProfileId);

        String schemaName = environment.getProperty("application.profiles.shcemaName");
        String sql;

        if(userType==1) {
            sql = "SELECT \n" +
                    "    ROWNUM ROWNO, \n" +
                    "    ID, \n" +
                    "    VEHICLE_ID, \n" +
                    "    ENGIN, \n" +
                    "    LAT, \n" +
                    "    LON, \n" +
                    "    VDATE, \n" +
                    "    VEH_MAINTENANCE, \n" +
                    "    ICON_TYPE\n" +
                    "FROM (\n" +
                    "    SELECT \n" +
                    "        ROWNUM ROWNO, \n" +
                    "        t.ID, \n" +
                    "        t.VEHICLE_ID, \n" +
                    "        t.ENGIN, \n" +
                    "        t.LAT, \n" +
                    "        t.LON, \n" +
                    "        TO_CHAR(TO_DATE(t.VDATE, 'YYYY-MM-DD HH24:MI:SS')-2 / 24, 'YYYY-MM-DD HH24:MI:SS') AS VDATE,\n" +
                    "        "+schemaName+"GET_VEHICLE_SERVICE_STAT(t.VEHICLE_ID) AS VEH_MAINTENANCE,\n" +
                    "        t.ICON_TYPE\n" +
                    "    FROM \n" +
                    "        "+schemaName+"NEX_INDIVIDUAL_TEMP t\n" +
                    "    WHERE \n" +
                    "        t.GROUP_ID = "+parentProfileId+" \n" +
                    "        AND (\n" +
                    "            t.VEHICLE_ID = "+SPECIFIC_VEHICLE_ID+"\n" +
                    "            OR "+SPECIFIC_VEHICLE_ID+" IS NULL\n" +
                    "        )\n" +
                    ") \n" +
                    "ORDER BY \n" +
                    "    ID ASC";
        }else {
            sql="select \n" +
                    "    ROWNUM ROWNO, \n" +
                    "    VEHICLE_ID, ENGIN, LAT, LON, VDATE, VEH_MAINTENANCE, ICON_TYPE,\n" +
                    "    DEPT_ID\n" +
                    "FROM\n" +
                    "   (\n" +
                    "    select \n" +
                    "        ROWNUM ROWNO, \n" +
                    "        t.VEHICLE_ID, t.ENGIN, t.LAT, t.LON, \n" +
                    "        to_char(to_date(t.VDATE, 'YYYY-MM-DD HH24:MI:SS')-2 / 24, 'YYYY-MM-DD HH24:MI:SS') as VDATE,\n" +
                    "        "+schemaName+"get_vehicle_service_stat(t.VEHICLE_ID) as VEH_MAINTENANCE,\n" +
                    "        t.ICON_TYPE,        \n" +
                    "        d.profile_id as DEPT_ID\n" +
                    "    FROM "+schemaName+" nex_individual_temp t, "+schemaName+"NEX_EXTENDED_USER_VS_VEHICLE d     \n" +
                    "    where d.profile_id = "+userId+"\n" +
                    "    and d.profile_type = '2'\n" +
                    "    and d.parent_profile_id = "+parentProfileId+"     \n" +
                    "    and t.VEHICLE_ID = d.VEHICLE_ID \n" +
                    "     AND (\n" +
                    "            t.VEHICLE_ID = "+SPECIFIC_VEHICLE_ID+"\n" +
                    "            OR "+SPECIFIC_VEHICLE_ID+" IS NULL\n" +
                    "        )   \n" +
                    "    order by t.ID asc\n" +
                    ")";
        }
        System.out.println(sql);

        Optional<ArrayList<VehicleStateInfoOra>> vehicleStateInfoList;

        try {

            vehicleStateInfoList = Optional.of((ArrayList<VehicleStateInfoOra>) jdbcTemplate.query(sql, new VehicleStateInfo_RowMapper(), new Object[]{}));

        } catch (BadSqlGrammarException e) {
            e.printStackTrace();
            logger.trace("No Data found with parentProfileId is {}  Sql Grammar Exception", parentProfileId);
            throw new AppCommonException(4001 + "##Sql Grammar Exception##1##1");
        } catch (TransientDataAccessException f) {
            logger.trace("No Data found with parentProfileId is {} network or driver issue or db is temporarily unavailable  ", parentProfileId);
            throw new AppCommonException(4002 + "##Network or driver issue or db is temporarily unavailable");
        } catch (CannotGetJdbcConnectionException g) {
            logger.trace("No Data found with parentProfileId is {} could not acquire a jdbc connection  ", parentProfileId);
            throw new AppCommonException(4003 + "##A database connection could not be obtained");
        }

        if (vehicleStateInfoList.isEmpty()) {
            return Optional.empty();
        } else {
            return vehicleStateInfoList;
        }
    }

    @Override
    public int Demo(Integer parentProfileId) {
        //String sql1 = "INSERT INTO my_temp_table VALUES (1, 'ONE') SELECT COUNT(*) FROM my_temp_table; commit";
        String sql = "INSERT INTO my_temp_table (ID, DESCRIPTION) VALUES (?, ?)";
        Object[] params = {1, "ONE"};
      //  performTransaction();

        try {
            jdbcTemplate.update(sql, params);
            System.out.println("Data inserted successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            // Handle exception as needed
        }
        int countFirstQuery = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM my_temp_table", Integer.class);
        System.out.println("countFirstQuery:" + countFirstQuery);
      //  jdbcTemplate.execute("COMMIT");
        int countSecondtQuery = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM my_temp_table", Integer.class);
        System.out.println("countSecondtQuery:" + countSecondtQuery);

        return countSecondtQuery;

    }

    public void performTransaction() {
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                // Perform multiple JDBC operations (queries, updates, etc.) here
                //jdbcTemplate.update("INSERT INTO my_temp_table VALUES (?, ?)", 1, "ONE");
              //  int countFirstQuery = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM my_temp_table", Integer.class);
              //  System.out.println("countFirstQuery:" + countFirstQuery);
                // If an exception occurs, the transaction will be rolled back automatically
                // If no exception occurs, the transaction will be committed automatically
            }
        });
    }


}
