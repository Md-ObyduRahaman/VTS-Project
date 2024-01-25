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
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

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
    private PlatformTransactionManager transactionManager;

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
            throw new AppCommonException(4001 + "##Sql Grammar Exception##" + deviceType + "##" + API_VERSION);
        }catch (TransientDataAccessException f){
            logger.trace("No Data found with vehicleId is {} network or driver issue or db is temporarily unavailable  ", vehicleId);
            throw new AppCommonException(4002 + "##Network or driver issue or db is temporarily unavailable##" + deviceType + "##" + API_VERSION);
        }catch (CannotGetJdbcConnectionException g){
            logger.trace("No Data found with vehicleId is {} could not acquire a jdbc connection  ", vehicleId);
            throw new AppCommonException(4003 + "##A database connection could not be obtained##" + deviceType + "##" + API_VERSION);
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
    public Optional<ArrayList<SpeedDataResponse>> getSpeedDataForgr(String finalToTime, String finalFromTime, Integer vehicleId, Integer deviceType) {

        // Define transaction attributes
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);

        String shcemaName = environment.getProperty("application.profiles.shcemaName");

        String sql="select ID,TIME_IN_NUMBER date_time,POSITION, SPEED FROM "+shcemaName+"NEX_HISTORYRECV_GTT_EX where VEHICLEID = to_char("+vehicleId+") and TIME_IN_NUMBER between "+finalFromTime+" and "+finalToTime+" order by id asc";
        String callProcedureSql = "CALL "+shcemaName+"PROC_HIS_DATA_TD_EX(?, ?,?,?)"; // Replace with your procedure name and parameter placeholders

        Optional<ArrayList<SpeedDataResponse>> speedDataResponses = Optional.empty();

        try {
            jdbcTemplate.update(callProcedureSql, vehicleId,finalFromTime,finalToTime,0); // Set actual parameter values
            speedDataResponses = Optional.of((ArrayList<SpeedDataResponse>) jdbcTemplate.query(sql,
                    BeanPropertyRowMapper.newInstance(SpeedDataResponse.class)));
           // results = jdbcTemplate.queryForList(sql);
            transactionManager.commit(status);


        } catch (BadSqlGrammarException e) {
            logger.trace("No Data found with vehicleId is {}  Sql Grammar Exception", vehicleId);
            throw new AppCommonException(4001 + "##Sql Grammar Exception##" + deviceType + "##" + API_VERSION);
        }catch (TransientDataAccessException f){
            logger.trace("No Data found with vehicleId is {} network or driver issue or db is temporarily unavailable  ", vehicleId);
            throw new AppCommonException(4002 + "##Network or driver issue or db is temporarily unavailable##" + deviceType + "##" + API_VERSION);
        }catch (CannotGetJdbcConnectionException g){
            logger.trace("No Data found with vehicleId is {} could not acquire a jdbc connection  ", vehicleId);
            throw new AppCommonException(4003 + "##A database connection could not be obtained##" + deviceType + "##" + API_VERSION);
        }

        if (speedDataResponses.get().isEmpty())
        {
            return Optional.empty();
        }
        else {
            return speedDataResponses;
        }
    }


}
