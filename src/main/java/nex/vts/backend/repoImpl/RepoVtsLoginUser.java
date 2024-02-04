package nex.vts.backend.repoImpl;

import nex.vts.backend.dbentities.VTS_LOGIN_USER;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class RepoVtsLoginUser {

    private final Logger logger = LoggerFactory.getLogger(RepoVtsLoginUser.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    Environment environment;

    public Optional<VTS_LOGIN_USER> findByUserName(String userName,String password) {
        Integer operatorid = Integer.valueOf(environment.getProperty("application.profiles.operatorid"));


        logger.trace("Executing query to find user by username: {}", userName);
        Optional<VTS_LOGIN_USER> userObj = Optional.empty();
        String query;
        if(!password.isEmpty()) {
            query = "" +
                    "SELECT ID," +
                    "       USERNAME," +
                    "       PASSWORD," +
                    "       PROFILE_ID," +
                    "       MAIN_ACCOUNT_ID," +
                    "       USER_TYPE," +
                    "       ROLE_ID," +
                    "       IS_ACCOUNT_ACTIVE," +
                    "       IS_REMOTE_ACCESS_ENABLED," +
                    "       PARENT_PROFILE_ID," +
                    "       OPERATORID," +
                    "          LPAD (OPERATORID, 2, '0')" +
                    "       || LPAD (DECODE (USER_TYPE, 1, PROFILE_ID, MAIN_ACCOUNT_ID), 6, '0')    CUSTOMER_ID" +
                    "  FROM (SELECT ID," +
                    "               USERNAME," +
                    "               PASSWORD," +
                    "               PROFILE_ID," +
                    "               MAIN_ACCOUNT_ID," +
                    "               USER_TYPE," +
                    "               ROLE_ID," +
                    "               IS_ACCOUNT_ACTIVE," +
                    "               IS_REMOTE_ACCESS_ENABLED," +
                    "               PARENT_PROFILE_ID," +
                    "               OPERATORID" +
                    "          FROM vts_login_user" +
                    "         WHERE " +
                    "IS_ACCOUNT_ACTIVE = 1 and OPERATORID = "+operatorid
                    +"and USERNAME = '" + userName + "' AND PASSWORD = '" + password + "')";
        }
        else {
            query = "" +
                    "SELECT ID," +
                    "       USERNAME," +
                    "       PASSWORD," +
                    "       PROFILE_ID," +
                    "       MAIN_ACCOUNT_ID," +
                    "       USER_TYPE," +
                    "       ROLE_ID," +
                    "       IS_ACCOUNT_ACTIVE," +
                    "       IS_REMOTE_ACCESS_ENABLED," +
                    "       PARENT_PROFILE_ID," +
                    "       OPERATORID," +
                    "          LPAD (OPERATORID, 2, '0')" +
                    "       || LPAD (DECODE (USER_TYPE, 1, PROFILE_ID, MAIN_ACCOUNT_ID), 6, '0')    CUSTOMER_ID" +
                    "  FROM (SELECT ID," +
                    "               USERNAME," +
                    "               PASSWORD," +
                    "               PROFILE_ID," +
                    "               MAIN_ACCOUNT_ID," +
                    "               USER_TYPE," +
                    "               ROLE_ID," +
                    "               IS_ACCOUNT_ACTIVE," +
                    "               IS_REMOTE_ACCESS_ENABLED," +
                    "               PARENT_PROFILE_ID," +
                    "               OPERATORID" +
                    "          FROM vts_login_user" +
                    "         WHERE USERNAME = '" + userName + "' )";
        }


        try {
            userObj = jdbcTemplate.query(query, new Object[]{}, (rs, rowNum) ->
                    new VTS_LOGIN_USER(
                            rs.getInt("ID"),
                            rs.getInt("PROFILE_ID"),
                            rs.getInt("MAIN_ACCOUNT_ID"),
                            rs.getInt("USER_TYPE"),
                            rs.getInt("ROLE_ID"),
                            rs.getInt("IS_ACCOUNT_ACTIVE"),
                            rs.getInt("IS_REMOTE_ACCESS_ENABLED"),
                            rs.getString("USERNAME"),
                            rs.getString("PASSWORD"),
                            rs.getString("PARENT_PROFILE_ID"),
                            rs.getString("OPERATORID"),
                            rs.getInt("CUSTOMER_ID")

                    )
            ).stream().findFirst();
        } catch (Exception e) {
            e.printStackTrace();
            if (e instanceof EmptyResultDataAccessException) {
                logger.trace("No user found with username {} on VTS_LOGIN_USER tbl", userName);

                return userObj;
            }
        }

        return userObj;
    }

}
