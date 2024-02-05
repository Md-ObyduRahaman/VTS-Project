package nex.vts.backend.repoImpl;

import nex.vts.backend.exceptions.AppCommonException;
import nex.vts.backend.models.responses.AccountSummaryInfo;
import nex.vts.backend.models.responses.UserFullName;
import nex.vts.backend.repositories.AccountSummaryRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.TransientDataAccessException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class AccountSummaryImpl implements AccountSummaryRepo {

    private final Logger logger = LoggerFactory.getLogger(AccountSummaryImpl.class);

    private final DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    Environment environment;
    private final short API_VERSION = 1;


    public AccountSummaryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<ArrayList<UserFullName>> getUserFullName(Integer profileId, Integer userType, Integer deviceType) {

        String schemaName = environment.getProperty("application.profiles.shcemaName");

        String sql = "SELECT " + schemaName + "get_client_name (" + profileId + ")    AS MOTHER_ACC_NAME, " + schemaName + "get_profile_name (" + userType + ", " + profileId + ", 0, 0)         AS FULL_NAME FROM DUAL";


        logger.trace(sql);
        Optional<ArrayList<UserFullName>> accountSummaries = Optional.empty();

        try {
            logger.trace(sql);

            accountSummaries = Optional.of((ArrayList<UserFullName>) jdbcTemplate.query(sql,
                    BeanPropertyRowMapper.newInstance(UserFullName.class)));
        } catch (BadSqlGrammarException e) {
            logger.trace("No Data found with profileId is {}  Sql Grammar Exception", profileId);
            throw new AppCommonException(4001 + "##Sql Grammar Exception##" + deviceType + "##" + API_VERSION);
        } catch (TransientDataAccessException f) {
            logger.trace("No Data found with profileId is {} network or driver issue or db is temporarily unavailable  ", profileId);
            throw new AppCommonException(4002 + "##Network or driver issue or db is temporarily unavailable" + deviceType + "##" + API_VERSION);
        } catch (CannotGetJdbcConnectionException g) {
            logger.trace("No Data found with profileId is {} could not acquire a jdbc connection  ", profileId);
            throw new AppCommonException(4003 + "##A database connection could not be obtained" + deviceType + "##" + API_VERSION);
        }

        if (accountSummaries.get().isEmpty()) {
            return Optional.empty();
        } else {
            return accountSummaries;
        }

    }

    @Override
    public Optional<ArrayList<AccountSummaryInfo>> getVehicleDataforM2m(Integer profileType, Integer profileId, Integer parentId, Integer deviceType) {

        Integer result = 0;
        String sql = null;
        String schemaName = environment.getProperty("application.profiles.shcemaName");


        sql = "select " +
                schemaName + "get_summary_info('TV'," + profileType + "," + profileId + "," + parentId + ",0,0) TOTAL_VEHICLE," +
                schemaName + "get_summary_info('AS'," + profileType + "," + profileId + "," + parentId + ",0,0) available_sms, " +
                schemaName + "get_summary_info('RV'," + profileType + "," + profileId + "," + parentId + ",0,0) running_now, " +
                schemaName + "get_summary_info('SV'," + profileType + "," + profileId + "," + parentId + ",0,0) stop_now, " +
                schemaName + "get_summary_info('TRV'," + profileType + "," + profileId + "," + parentId + ",0,0) today_running, " +
                schemaName + "get_distance_summary(" + profileType + ", " + profileId + "," + parentId + ", to_char(sysdate,'YYYYMMDD'),to_char(sysdate,'YYYYMMDD')) todays_distance, " +
                schemaName + "get_alert_summary('SPEED', " + profileType + ", " + profileId + ", " + parentId + ", to_char(trunc(sysdate),'YYYYMMDDHH24MISS'), to_char(sysdate,'YYYYMMDDHH24MISS')) todays_speed_alert, " +
                schemaName + "get_alert_summary('ALLALERT', " + profileType + ", " + profileId + ", " + parentId + ", to_char(trunc(sysdate),'YYYYMMDDHH24MISS'), to_char(sysdate,'YYYYMMDDHH24MISS')) todays_alert, " +
                schemaName + "get_summary_info('DS'," + profileType + "," + profileId + "," + parentId + ",0,0) driverScore from dual";

        System.out.println(sql);
        logger.trace(sql);

        Optional<ArrayList<AccountSummaryInfo>> accountSummaries;
        try {
            accountSummaries = Optional.of((ArrayList<AccountSummaryInfo>) jdbcTemplate.query(sql,
                    BeanPropertyRowMapper.newInstance(AccountSummaryInfo.class)));
        } catch (BadSqlGrammarException e) {
            logger.trace("No Data found with profileId is {}  Sql Grammar Exception", profileId);
            throw new AppCommonException(4001 + "##Sql Grammar Exception##" + deviceType + "##" + API_VERSION);
        } catch (TransientDataAccessException f) {
            logger.trace("No Data found with profileId is {} network or driver issue or db is temporarily unavailable  ", profileId);
            throw new AppCommonException(4002 + "##Network or driver issue or db is temporarily unavailable##" + deviceType + "##" + API_VERSION);
        } catch (CannotGetJdbcConnectionException g) {
            logger.trace("No Data found with profileId is {} could not acquire a jdbc connection  ", profileId);
            throw new AppCommonException(4003 + "##A database connection could not be obtained##" + deviceType + "##" + API_VERSION);
        }


        return accountSummaries;
    }

    @Override
    public Optional<ArrayList<AccountSummaryInfo>> getVehicleDataforGP(Integer profileType, Integer profileId, Integer parentId, Integer deviceType) {
        Integer result = 0;
        String sql = null;
        String schemaName = environment.getProperty("application.profiles.shcemaName");


        sql = "select " + schemaName + "get_summary_info('TV'," + profileType + "," + profileId + "," + parentId + ",0,0) TOTAL_VEHICLE, " + schemaName + "get_summary_info('AS'," + profileType + "," + profileId + "," + parentId + ",0,0) available_sms, " + schemaName + "get_summary_info('RV'," + profileType + "," + profileId + "," + parentId + ",0,0) running_now, " + schemaName + "get_summary_info('SV'," + profileType + "," + profileId + "," + parentId + ",0,0) stop_now, " + schemaName + "get_summary_info('TRV'," + profileType + "," + profileId + "," + parentId + ",0,0) today_running, " + schemaName + "get_distance_summary(" + profileType + ", " + profileId + "," + parentId + ", to_char(sysdate,'YYYYMMDD'),to_char(sysdate,'YYYYMMDD')) todays_distance, " + schemaName + "get_alert_summary('SPEED', " + profileType + ", " + profileId + ", " + parentId + ", to_char(trunc(sysdate),'YYYYMMDDHH24MISS'), to_char(sysdate,'YYYYMMDDHH24MISS')) todays_speed_alert, " + schemaName + "get_alert_summary('ALLALERT', " + profileType + ", " + profileId + ", " + parentId + ", to_char(trunc(sysdate),'YYYYMMDDHH24MISS'), to_char(sysdate,'YYYYMMDDHH24MISS')) todays_alert, " + schemaName + "get_summary_info('DC'," + profileType + "," + profileId + "," + parentId + ",0,0) driverScore from dual";

        System.out.println(sql);
        logger.trace(sql);

        Optional<ArrayList<AccountSummaryInfo>> accountSummaries;
        try {
            accountSummaries = Optional.of((ArrayList<AccountSummaryInfo>) jdbcTemplate.query(sql,
                    BeanPropertyRowMapper.newInstance(AccountSummaryInfo.class)));
        } catch (BadSqlGrammarException e) {
            logger.trace("No Data found with profileId is {}  Sql Grammar Exception", profileId);
            throw new AppCommonException(4001 + "##Sql Grammar Exception##" + deviceType + "##" + API_VERSION);
        } catch (TransientDataAccessException f) {
            logger.trace("No Data found with profileId is {} network or driver issue or db is temporarily unavailable  ", profileId);
            throw new AppCommonException(4002 + "##Network or driver issue or db is temporarily unavailable##" + deviceType + "##" + API_VERSION);
        } catch (CannotGetJdbcConnectionException g) {
            logger.trace("No Data found with profileId is {} could not acquire a jdbc connection  ", profileId);
            throw new AppCommonException(4003 + "##A database connection could not be obtained##" + deviceType + "##" + API_VERSION);
        }


        return accountSummaries;
    }


}
