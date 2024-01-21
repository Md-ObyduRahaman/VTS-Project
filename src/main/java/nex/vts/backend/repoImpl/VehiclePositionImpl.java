package nex.vts.backend.repoImpl;

import nex.vts.backend.exceptions.AppCommonException;
import nex.vts.backend.models.responses.VehicleOthersInfoModel;
import nex.vts.backend.models.responses.VehiclePositionReportData;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VehiclePositionImpl implements VehiclePositionRepo {
    private final short API_VERSION = 1;
    @Autowired
    Environment environment;

    private final Logger logger = LoggerFactory.getLogger(VehicleOthersInfoImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Optional<ArrayList<VehiclePositionReportData>> findVehiclePositionRepo(String userId, Integer vehicleId, String fromDate, String toDate, String locationStat, int deviceType, int userType) {
        String shcemaName = environment.getProperty("application.profiles.shcemaName");

        Optional<ArrayList<VehiclePositionReportData>> datalList;
        String sql = null;
        String outterSql, innerSql = null;
        if (userType == 1) {

            if (locationStat.equals("1")) {
                outterSql = "and (e.S_ENGINE_STAT = 'ON') order by e.VEHICLEID asc, e.S_TIME asc";
            } else {
                outterSql = " and (e.E_ENGINE_STAT = 'OFF')  order by e.VEHICLEID asc, e.E_TIME asc";
            }
            if (vehicleId > 0) {
                innerSql = "and VEHICLEID = '" + vehicleId + "'";
            } else {
                innerSql = "";
            }
            sql = "SELECT VEHICLEID,\n" +
                    "    ROW_NUMBER() OVER (ORDER BY e.VEHICLEID ASC, e.S_TIME ASC) AS SL,     GROUPID,\n" +
                    "         DATE_IN_NUMBER,\n" +
                    "         S_ENGINE_STAT,\n" +
                    "         S_LAT,\n" +
                    "         S_LON,\n" +
                    "         TO_CHAR (TO_DATE (S_TIME, 'YYYY-MM-DD HH24:MI:SS') - 2 / 24,\n" +
                    "                  'YYYY-MM-DD HH24:MI:SS')        AS S_TIME,\n" +
                    "         E_ENGINE_STAT,\n" +
                    "         E_LAT,\n" +
                    "         E_LON,\n" +
                    "         TO_CHAR (TO_DATE (E_TIME, 'YYYY-MM-DD HH24:MI:SS') - 2 / 24,\n" +
                    "                  'YYYY-MM-DD HH24:MI:SS')        AS E_TIME,\n" +
                    "         "+shcemaName+"get_vehicle_name (GROUPID, VEHICLEID)    AS V_NAME,\n" +
                    "         "+shcemaName+"get_driver_name (VEHICLEID)              AS V_DRIVER_NAME,\n" +
                    "         v.CAR_MODEL                              AS V_CAR_MODEL\n" +
                    "    FROM (SELECT VEHICLEID,\n" +
                    "                 GROUPID,\n" +
                    "                 DATE_IN_NUMBER,\n" +
                    "                 S_ENGINE_STAT,\n" +
                    "                 S_LAT,\n" +
                    "                 S_LON,\n" +
                    "                 S_TIME,\n" +
                    "                 E_ENGINE_STAT,\n" +
                    "                 E_LAT,\n" +
                    "                 E_LON,\n" +
                    "                 E_TIME\n" +
                    "            FROM GPSNEXM2M.NEX_ENGIN_STAT_MW\n" +
                    "           WHERE     GROUPID = '" + userId + "'\n" +

                    "\n" + innerSql +
                    "                 AND DATE_IN_NUMBER BETWEEN ('" + fromDate + "') AND ('" + toDate + "')) e,\n" +
                    "         NEX_INDIVIDUAL_CLIENT v\n" +
                    "   WHERE e.VEHICLEID = v.ID AND v.ACTIVATION = 1 \n" + outterSql;


        }
        try {
            ArrayList<VehiclePositionReportData> vehiclePositionReportDataList = (ArrayList<VehiclePositionReportData>) jdbcTemplate.query(sql, (rs, rowNum) -> {
                VehiclePositionReportData vehiclePositionReportData = new VehiclePositionReportData();
                vehiclePositionReportData.setSl(rs.getString("SL"));

                //Location Stat: 1 = Start,2 = End

                if (locationStat.equals("1")) {
                    vehiclePositionReportData.setDateTime(rs.getString("S_TIME"));
                    vehiclePositionReportData.setEngStat(rs.getString("S_ENGINE_STAT"));
                    vehiclePositionReportData.setLocationDetails(rs.getString("S_LAT")+","+rs.getString("S_LON"));
                } else {
                    vehiclePositionReportData.setDateTime(rs.getString("E_TIME"));
                    vehiclePositionReportData.setEngStat(rs.getString("E_ENGINE_STAT"));
                    vehiclePositionReportData.setLocationDetails(rs.getString("E_LAT")+","+rs.getString("E_LON"));
                }
                vehiclePositionReportData.setVehName(rs.getString("V_CAR_MODEL"));
                vehiclePositionReportData.setVehId(rs.getString("VEHICLEID"));


                return vehiclePositionReportData;
            });

            datalList = Optional.ofNullable(vehiclePositionReportDataList);
        } catch (BadSqlGrammarException e) {
            logger.trace("No Data found with userId is {}  Sql Grammar Exception", userId);
            throw new AppCommonException(4001 + "##Sql Grammar Exception" + deviceType + "##" + API_VERSION);
        } catch (TransientDataAccessException f) {
            logger.trace("No Data found with userId is {} network or driver issue or db is temporarily unavailable  ", userId);
            throw new AppCommonException(4002 + "##Network or driver issue or db is temporarily unavailable" + deviceType + "##" + API_VERSION);
        } catch (CannotGetJdbcConnectionException g) {
            logger.trace("No Data found with userId is {} could not acquire a jdbc connection  ", userId);
            throw new AppCommonException(4003 + "##A database connection could not be obtained" + deviceType + "##" + API_VERSION);
        }

        return datalList;

    }
}
