package nex.vts.backend.repoImpl;

import nex.vts.backend.exceptions.AppCommonException;
import nex.vts.backend.models.responses.MonthTravleDistance;
import nex.vts.backend.models.responses.MonthTravleDistanceForAll;
import nex.vts.backend.models.responses.TravelDistanceDataModel;
import nex.vts.backend.repositories.TravelDistanceDataRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.TransientDataAccessException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class TravelDistanceDataImpl implements TravelDistanceDataRepo {

    private final Logger logger = LoggerFactory.getLogger(TravelDistanceDataImpl.class);

    private final short API_VERSION = 1;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final DataSource dataSource;
    SimpleJdbcCall getAllStatesJdbcCall;
    @Autowired
    Environment environment;

    List<Map<String, Object>> results;


    @Autowired
    public TravelDistanceDataImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    @Override
    public MonthTravleDistanceForAll getTravelDistanceData(TravelDistanceDataModel t,Integer deviceType) throws SQLException {

        String shcemaName = environment.getProperty("application.profiles.shcemaName");


        String sql="SELECT\n" +
                "   ROW_NUMBER() OVER (ORDER BY DATETIME) AS key,\n" +
                "    GROUPID AS profile_id,\n" +
                "    VEHICLEID AS VEHICLE_ID,\n" +
                "    DATETIME AS DATE_TIME,\n" +
                "    NUM_OF_DAYS,\n" +
                "    ROUND(DISTANCE,2) as DISTANCE,\n" +
                "    MOTHER_ACCOUNT_NAME AS main_account_id,\n" +
                "    ROUND(AVG(DISTANCE) OVER (), 2) AS average_distance,\n" +
                "    ROUND(SUM(DISTANCE) OVER (), 2)  AS total_distance,\n" +
                "    ROUND(MAX(DISTANCE) OVER (), 2) AS MAX_DISTANCE,\n" +
                "    ROUND(MIN(DISTANCE) OVER (), 2) AS MIN_DISTANCE,\n" +
                "    ROUND(COUNT(*) OVER (), 0) AS totalRowCount\n" +
                "FROM "+shcemaName+"NEX_DISTANCE_REPOT_DATA";

        // Step 1: Call the stored procedure with parameters
        String callProcedureSql = "CALL "+shcemaName+"GENERATE_DISTANCE_REPORT_DATA(?, ?,?,?,?,?,?,?,?)"; // Replace with your procedure name and parameter placeholders

      try {
          jdbcTemplate.update(callProcedureSql, "DISTANCE", "D",t.getProfileType(),t.getProfileId(),t.getParentId(),t.getP_all_vehicle_flag(),t.getVehicleId(),t.getP_date_from(),t.getP_date_to()); // Set actual parameter values
          // Step 2: Run a SELECT query to fetch the results
          results = jdbcTemplate.queryForList(sql);
      }
      catch (BadSqlGrammarException e) {
          logger.trace("No Data found with vehicleId is {}  Sql Grammar Exception", t.getVehicleId());
          throw new AppCommonException(4001 + "##Sql Grammar Exception" + deviceType + "##" + API_VERSION);
      }catch (TransientDataAccessException f){
          logger.trace("No Data found with vehicleId is {} network or driver issue or db is temporarily unavailable  ", t.getVehicleId());
          throw new AppCommonException(4002 + "##Network or driver issue or db is temporarily unavailable" + deviceType + "##" + API_VERSION);
      }catch (CannotGetJdbcConnectionException g){
          logger.trace("No Data found with vehicleId is {} could not acquire a jdbc connection  ", t.getVehicleId());
          throw new AppCommonException(4003 + "##A database connection could not be obtained" + deviceType + "##" + API_VERSION);
      }



        //Map Data
        ArrayList<MonthTravleDistance> monthTravleDistanceList=new ArrayList<>();
        MonthTravleDistanceForAll monthTravleDistanceForAllin=new MonthTravleDistanceForAll();
        Boolean flag=true;

        for (Map<String, Object> map : results) {

            MonthTravleDistance monthTravleDistance=new MonthTravleDistance();
            if(flag) {
                BigDecimal AVERAGE_DISTANCE = (BigDecimal) map.get("AVERAGE_DISTANCE");
                monthTravleDistanceForAllin.setAverage(AVERAGE_DISTANCE.doubleValue());
                BigDecimal TOTAL_DISTANCE = (BigDecimal) map.get("TOTAL_DISTANCE");
                monthTravleDistanceForAllin.setTotal(TOTAL_DISTANCE.doubleValue());
                BigDecimal MAX_DISTANCE = (BigDecimal) map.get("MAX_DISTANCE");
                monthTravleDistanceForAllin.setMax(MAX_DISTANCE.doubleValue());
                BigDecimal MIN_DISTANCE = (BigDecimal) map.get("MIN_DISTANCE");
                monthTravleDistanceForAllin.setMin(MIN_DISTANCE.doubleValue());
                BigDecimal TOTALROWCOUNT = (BigDecimal) map.get("TOTALROWCOUNT");
                monthTravleDistanceForAllin.setTotalCount(TOTALROWCOUNT.intValue());
                flag=false;

}
            String PROFILE_ID = (String) map.get("PROFILE_ID");
            String VEHICLE_ID = (String) map.get("VEHICLE_ID");
            String DATE_TIME = (String) map.get("DATE_TIME");
            BigDecimal NUM_OF_DAYS = (BigDecimal) map.get("NUM_OF_DAYS");
            BigDecimal DISTANCE = (BigDecimal) map.get("DISTANCE");
            String MAIN_ACCOUNT_ID = (String) map.get("MAIN_ACCOUNT_ID");
            BigDecimal KEY = (BigDecimal) map.get("KEY");

            monthTravleDistanceList.add(new MonthTravleDistance(PROFILE_ID, VEHICLE_ID,
                    DATE_TIME, NUM_OF_DAYS.toString(),
                    DISTANCE.toString(), MAIN_ACCOUNT_ID, KEY.intValue()));
        }
        monthTravleDistanceForAllin.setMonthTravleDistancesList(monthTravleDistanceList);



        return monthTravleDistanceForAllin;

    }


}

