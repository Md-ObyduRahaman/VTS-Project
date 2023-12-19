package nex.vts.backend.repoImpl;

import nex.vts.backend.dbentities.VTS_LOGIN_USER;
import nex.vts.backend.exceptions.AppCommonException;

import nex.vts.backend.models.responses.SpeedDataReport;
import nex.vts.backend.models.responses.SpeedDataResponse;
import nex.vts.backend.repositories.SpeedDataRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.TransientDataAccessException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class SpeedDataImpl implements SpeedDataRepo {

    private final Logger logger = LoggerFactory.getLogger(SpeedDataImpl.class);
    private final short API_VERSION = 1;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final DataSource dataSource;
    SimpleJdbcCall getAllStatesJdbcCall;
    @Autowired
    Environment environment;

    List<Map<String, Object>> results;


    @Autowired
    public SpeedDataImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    @Override
    public Optional<ArrayList<SpeedDataResponse>> getSpeedDataForhr(String finalToTime, String finalFromTime, Integer vehicleId,Integer deviceType) {

        String sql=" SELECT ID,\n" +
                "         TIME_IN_NUMBER date_time,\n" +
                "         POSITION,\n" +
                "         SPEED\n" +
                "    FROM (SELECT ID,\n" +
                "                 TIME_IN_NUMBER,\n" +
                "                 POSITION,\n" +
                "                 SPEED\n" +
                "            FROM nex_historyrecv\n" +
                "           WHERE vehicleid = TO_CHAR ("+vehicleId+"))\n" +
                "   WHERE TIME_IN_NUMBER BETWEEN "+finalFromTime+" AND "+finalToTime+"\n" +
                "ORDER BY id ASC";


        Optional<ArrayList<SpeedDataResponse>> speedDataResponses = Optional.empty();

        try {

            speedDataResponses = Optional.of((ArrayList<SpeedDataResponse>) jdbcTemplate.query(sql,
                    BeanPropertyRowMapper.newInstance(SpeedDataResponse.class)));

        } catch (BadSqlGrammarException e) {
            logger.trace("No Data found with vehicleId is {}  Sql Grammar Exception", vehicleId);
            throw new AppCommonException(4001 + "##Sql Grammar Exception" + deviceType + "##" + API_VERSION);
        }catch (TransientDataAccessException f){
            logger.trace("No Data found with vehicleId is {} network or driver issue or db is temporarily unavailable  ", vehicleId);
            throw new AppCommonException(4002 + "##Network or driver issue or db is temporarily unavailable" + deviceType + "##" + API_VERSION);
        }catch (CannotGetJdbcConnectionException g){
            logger.trace("No Data found with vehicleId is {} could not acquire a jdbc connection  ", vehicleId);
            throw new AppCommonException(4003 + "##A database connection could not be obtained" + deviceType + "##" + API_VERSION);
        }

        if (speedDataResponses.get().isEmpty())
        {
            return Optional.empty();
        }
        else {
            return speedDataResponses;
        }
    }

    @Override
    public Optional<ArrayList<SpeedDataReport>> getSpeedDataForMithuVai(String finalToTime, String finalFromTime, Integer vehicleStatus, Integer deviceType, VTS_LOGIN_USER t) {
        String shcemaName = environment.getProperty("application.profiles.shcemaName");

        Character report_type=null;
        Integer vehicle_id,all_vehicle_flag;
        String where_sql,sql=null;

        if(vehicleStatus == 1){
            report_type = 'S';
            vehicle_id = 0;
            all_vehicle_flag = 1;
            where_sql = "";
        }else if(vehicleStatus == 2){
            report_type = 'D';
            vehicle_id = 0;
            all_vehicle_flag = 1;
            where_sql = " AND DATETIME BETWEEN '"+finalFromTime +"' AND '"+finalToTime+"'";
        }else{
            report_type = 'D';
            vehicle_id = vehicleStatus;
            all_vehicle_flag = 0;
            where_sql = " AND DATETIME BETWEEN '"+finalFromTime+"' AND '"+finalToTime+"'";
        }

        if (all_vehicle_flag==1) {
            sql = "SELECT ROWNUM,a.GROUPID,a.VEHICLEID,a.DATETIME,a.NUM_OF_DAYS,a.DISTANCE,a.MOTHER_ACCOUNT_NAME,a.VEHICLE_NAME, a.COMPANY_NAME\n" +
                    "                    from\n" +
                    "                    (\n" +
                    "                    SELECT ROWNUM,a.GROUPID,a.VEHICLEID,a.DATETIME,a.NUM_OF_DAYS,a.DISTANCE,a.MOTHER_ACCOUNT_NAME,b.userid VEHICLE_NAME, GET_CLIENT_NAME("+t.getPROFILE_ID()+") COMPANY_NAME\n" +
                    "                    FROM NEX_DISTANCE_REPOT_DATA a,nex_individual_client b\n" +
                    "                    where a.vehicleid(+)=B.ID\n" +
                    "                    and b.company_id="+t.getPROFILE_ID()+"" +
                    "                    ) a \n" +
                    "                    order by a.vehicle_name, a.DATETIME ASC";
        } else if (all_vehicle_flag==0){
            sql="SELECT ROWNUM,a.GROUPID,a.VEHICLEID,a.DATETIME,a.NUM_OF_DAYS,a.DISTANCE,a.MOTHER_ACCOUNT_NAME,a.VEHICLE_NAME, a.COMPANY_NAME\n" +
                    "                    from\n" +
                    "                    (\n" +
                    "                    SELECT ROWNUM,a.GROUPID,a.VEHICLEID,a.DATETIME,a.NUM_OF_DAYS,a.DISTANCE,a.MOTHER_ACCOUNT_NAME,b.userid VEHICLE_NAME, GET_CLIENT_NAME("+t.getPROFILE_ID()+") COMPANY_NAME\n" +
                    "                    FROM NEX_DISTANCE_REPOT_DATA a,nex_individual_client b\n" +
                    "                    where a.vehicleid(+)=B.ID\n" +
                    "                    and b.company_id="+t.getPROFILE_ID()+"" +
                    "                    and B.ID = "+vehicle_id+"\n" +
                    "                    ) a \n" +
                    "                    order by a.vehicle_name, a.DATETIME ASC";

        }else if (all_vehicle_flag==2){
            sql="SELECT ROWNUM,a.GROUPID,a.VEHICLEID,a.DATETIME,a.NUM_OF_DAYS,a.DISTANCE,a.MOTHER_ACCOUNT_NAME,a.VEHICLE_NAME, a.COMPANY_NAME\n" +
                    "                    from\n" +
                    "                    (\n" +
                    "                    SELECT ROWNUM,a.GROUPID,a.VEHICLEID,a.DATETIME,a.NUM_OF_DAYS,a.DISTANCE,a.MOTHER_ACCOUNT_NAME,b.userid VEHICLE_NAME, GET_CLIENT_NAME("+t.getPROFILE_ID()+") COMPANY_NAME\n" +
                    "                    FROM NEX_DISTANCE_REPOT_DATA a,nex_individual_client b\n" +
                    "                    where a.vehicleid(+)=B.ID\n" +
                    "                    and b.company_id="+t.getPROFILE_ID()+"" +
                    "                    and B.ID = "+vehicle_id+"\n" +
                    "                    ) a \n" +
                    "                    order by a.vehicle_name, a.DATETIME ASC";

        }
        System.out.println(sql);


        Optional<ArrayList<SpeedDataReport>> speedDataReports = Optional.empty();

        try {
            String callProcedureSql = "CALL "+shcemaName+"GENERATE_DISTANCE_REPORT_DATA(?, ?,?,?,?,?,?,?,?)"; // Replace with your procedure name and parameter placeholders
            jdbcTemplate.update(callProcedureSql, "DISTANCE", report_type,t.getUSER_TYPE(),t.getPROFILE_ID(),t.getPARENT_PROFILE_ID(),all_vehicle_flag,vehicle_id,finalFromTime,finalToTime);
            // Set actual parameter values
            speedDataReports = Optional.of((ArrayList<SpeedDataReport>) jdbcTemplate.query(sql,
                    BeanPropertyRowMapper.newInstance(SpeedDataReport.class)));
        } catch (BadSqlGrammarException e) {
            logger.trace("No Data found with Profile Id is {}  Sql Grammar Exception", t.getPROFILE_ID());
            throw new AppCommonException(4001 + "##Sql Grammar Exception" + deviceType + "##" + API_VERSION);
        }catch (TransientDataAccessException f){
            logger.trace("No Data found with Profile Id is {} network or driver issue or db is temporarily unavailable  ", t.getPROFILE_ID());
            throw new AppCommonException(4002 + "##Network or driver issue or db is temporarily unavailable" + deviceType + "##" + API_VERSION);
        }catch (CannotGetJdbcConnectionException g){
            logger.trace("No Data found with profile Id is {} could not acquire a jdbc connection  ", t.getPROFILE_ID());
            throw new AppCommonException(4003 + "##A database connection could not be obtained" + deviceType + "##" + API_VERSION);
        }

        if (speedDataReports.get().isEmpty())
        {
            return Optional.empty();
        }
        else {
            return speedDataReports;
        }
    }
}
