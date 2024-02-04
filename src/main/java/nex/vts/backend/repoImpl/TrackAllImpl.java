package nex.vts.backend.repoImpl;

import nex.vts.backend.exceptions.AppCommonException;
import nex.vts.backend.models.responses.OverSpeedData;
import nex.vts.backend.models.responses.SpeedReportDetails;
import nex.vts.backend.models.responses.TrackAllInfo;
import nex.vts.backend.repositories.TrackAllRepo;
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
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.ArrayList;
import java.util.Optional;

import static nex.vts.backend.utilities.ExtractLocationLib.get_Location;

@Service
public class TrackAllImpl implements TrackAllRepo {

    @Autowired
    Environment environment;
    @Autowired
    JdbcTemplate jdbcTemplate;
    private final Logger logger = LoggerFactory.getLogger(TrackAllImpl.class);

    @Override
    public Optional<ArrayList<TrackAllInfo>> getOverSpeedInfo(int userType,Long userId, Long p_userId, int deviceType, int apiVersion,String vehicleId) {

        String shcemaName = environment.getProperty("application.profiles.shcemaName");


        Optional<ArrayList<TrackAllInfo>> trackAllInfos;


        String sql = null;
        if(userType==1)
        {
            sql="/* Formatted on 2/1/2024 3:32:16 PM (QP5 v5.362) */" +
                    "SELECT ROWNUM     ROWNO," +
                    "       ID," +
                    "       VEHICLE_ID," +
                    "       USERID," +
                    "       GROUP_ID," +
                    "       LAT," +
                    "       LON," +
                    "       SPEED," +
                    "       ENGIN," +
                    "       VDATE," +
                    "       FAVORITE," +
                    "       ICON_TYPE" +
                    "  FROM (  SELECT t.ID," +
                    "                 t.VEHICLE_ID," +
                    "                 t.USERID," +
                    "                 t.GROUP_ID," +
                    "                 t.LAT," +
                    "                 t.LON," +
                    "                 t.SPEED," +
                    "                 t.ENGIN," +
                    "                 t.VDATE," +
                    "                 t.FAVORITE," +
                    "                 t.ICON_TYPE" +
                    "            FROM nex_individual_temp t" +
                    "           WHERE t.GROUP_ID IN" +
                    "                     (SELECT COMPANY_ID" +
                    "                        FROM NEX_INDIVIDUAL_CLIENT" +
                    "                       WHERE (COMPANY_ID = '"+p_userId+"' AND ACTIVATION = 1))" +
                    "        ORDER BY t.USERID ASC)";

        }
        else if(userType==2){
            sql="SELECT ROWNUM     ROWNO," +
                    "       ID," +
                    "       VEHICLE_ID," +
                    "       USERID," +
                    "       GROUP_ID," +
                    "       LAT," +
                    "       LON," +
                    "       SPEED," +
                    "       ENGIN," +
                    "       VDATE," +
                    "       FAVORITE," +
                    "       ICON_TYPE" +
                    "  FROM (  SELECT t.ID," +
                    "                 t.VEHICLE_ID," +
                    "                 t.USERID," +
                    "                 t.GROUP_ID," +
                    "                 t.LAT," +
                    "                 t.LON," +
                    "                 t.SPEED," +
                    "                 t.ENGIN," +
                    "                 t.VDATE," +
                    "                 t.FAVORITE," +
                    "                 t.ICON_TYPE" +
                    "            FROM nex_individual_temp t, nex_dept_wise_vehicle d" +
                    "           WHERE     (    d.COMPANY_ID ="+p_userId+"" +
                    "                      AND d.DEPT_ID = "+userId+"" +
                    "                      AND d.ACTIVATION = 1)" +
                    "                 AND t.GROUP_ID = d.COMPANY_ID" +
                    "                 AND t.VEHICLE_ID = d.VEHICLE_ID" +
                    "        ORDER BY t.USERID ASC)";
        }


        try {

            ArrayList<TrackAllInfo> trackAllInfoList = (ArrayList<TrackAllInfo>) jdbcTemplate.query(sql, (rs, rowNum) -> {
                TrackAllInfo trackAllInfo = new TrackAllInfo();
                trackAllInfo.setLat(rs.getString("LAT"));
                trackAllInfo.setSpeed(rs.getString("SPEED"));
                trackAllInfo.setLng(rs.getString("LON"));
                trackAllInfo.setDate(rs.getString("VDATE").substring(0,10));
                trackAllInfo.setEngin(rs.getString("ENGIN"));
                trackAllInfo.setIconType(rs.getString("ICON_TYPE"));
                trackAllInfo.setVehId(rs.getString("VEHICLE_ID"));
                trackAllInfo.setVehName(rs.getString("USERID"));
                trackAllInfo.setTime(rs.getString("VDATE").substring(11,19));
                return trackAllInfo;
            });
            trackAllInfos = Optional.of(trackAllInfoList);


        } catch (BadSqlGrammarException e) {
            logger.trace("No Data found with userId is {}  Sql Grammar Exception", userId);
            throw new AppCommonException(4001 + "##Sql Grammar Exception##" + deviceType + "##" + apiVersion);
        } catch (TransientDataAccessException f) {
            logger.trace("No Data found with userId is {} network or driver issue or db is temporarily unavailable  ", userId);
            throw new AppCommonException(4002 + "##Network or driver issue or db is temporarily unavailable##" + deviceType + "##" + apiVersion);
        } catch (CannotGetJdbcConnectionException g) {
            logger.trace("No Data found with userId is {} could not acquire a jdbc connection  ", userId);
            throw new AppCommonException(4003 + "##A database connection could not be obtained##" + deviceType + "##" + apiVersion);
        }

        if (trackAllInfos.get().isEmpty()) {

            return Optional.empty();

        } else {

            return trackAllInfos;
        }
    }
}
