package nex.vts.backend.repoImpl;

import nex.vts.backend.dbentities.VTS_LOGIN_USER;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class RepoVtsLoginUser {

    private final Logger logger = LoggerFactory.getLogger(RepoVtsLoginUser.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Optional<VTS_LOGIN_USER> findByUserName(String userName,String password) {

        logger.trace("Executing query to find user by username: {}", userName);
        Optional<VTS_LOGIN_USER> userObj = Optional.empty();
        String query;
        if(!password.isEmpty()) {
            query = "\n" +
                    "SELECT ID,\n" +
                    "       USERNAME,\n" +
                    "       PASSWORD,\n" +
                    "       PROFILE_ID,\n" +
                    "       MAIN_ACCOUNT_ID,\n" +
                    "       USER_TYPE,\n" +
                    "       ROLE_ID,\n" +
                    "       IS_ACCOUNT_ACTIVE,\n" +
                    "       IS_REMOTE_ACCESS_ENABLED,\n" +
                    "       PARENT_PROFILE_ID,\n" +
                    "       OPERATORID,\n" +
                    "          LPAD (OPERATORID, 2, '0')\n" +
                    "       || LPAD (DECODE (USER_TYPE, 1, PROFILE_ID, MAIN_ACCOUNT_ID), 6, '0')    CUSTOMER_ID\n" +
                    "  FROM (SELECT ID,\n" +
                    "               USERNAME,\n" +
                    "               PASSWORD,\n" +
                    "               PROFILE_ID,\n" +
                    "               MAIN_ACCOUNT_ID,\n" +
                    "               USER_TYPE,\n" +
                    "               ROLE_ID,\n" +
                    "               IS_ACCOUNT_ACTIVE,\n" +
                    "               IS_REMOTE_ACCESS_ENABLED,\n" +
                    "               PARENT_PROFILE_ID,\n" +
                    "               OPERATORID\n" +
                    "          FROM vts_login_user\n" +
                    "         WHERE USERNAME = '" + userName + "' AND PASSWORD = '" + password + "')";
        }
        else {
            query = "\n" +
                    "SELECT ID,\n" +
                    "       USERNAME,\n" +
                    "       PASSWORD,\n" +
                    "       PROFILE_ID,\n" +
                    "       MAIN_ACCOUNT_ID,\n" +
                    "       USER_TYPE,\n" +
                    "       ROLE_ID,\n" +
                    "       IS_ACCOUNT_ACTIVE,\n" +
                    "       IS_REMOTE_ACCESS_ENABLED,\n" +
                    "       PARENT_PROFILE_ID,\n" +
                    "       OPERATORID,\n" +
                    "          LPAD (OPERATORID, 2, '0')\n" +
                    "       || LPAD (DECODE (USER_TYPE, 1, PROFILE_ID, MAIN_ACCOUNT_ID), 6, '0')    CUSTOMER_ID\n" +
                    "  FROM (SELECT ID,\n" +
                    "               USERNAME,\n" +
                    "               PASSWORD,\n" +
                    "               PROFILE_ID,\n" +
                    "               MAIN_ACCOUNT_ID,\n" +
                    "               USER_TYPE,\n" +
                    "               ROLE_ID,\n" +
                    "               IS_ACCOUNT_ACTIVE,\n" +
                    "               IS_REMOTE_ACCESS_ENABLED,\n" +
                    "               PARENT_PROFILE_ID,\n" +
                    "               OPERATORID\n" +
                    "          FROM vts_login_user\n" +
                    "         WHERE USERNAME = '" + userName + "' )";
        }

        System.out.println(query);

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
