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

    public Optional<VTS_LOGIN_USER> findByUserName(String userName,String shcemaName) {

        logger.trace("Executing query to find user by username: {}", userName);
        Optional<VTS_LOGIN_USER> userObj = Optional.empty();
        String query = "SELECT *\n" +
                "FROM (SELECT *\n" +
                "      FROM "+shcemaName+"VTS_LOGIN_USER\n" +
                "      WHERE USERNAME = ?\n" +
                "      ORDER BY ID ASC)\n" +
                "WHERE ROWNUM <= 1";

        try {
            userObj = jdbcTemplate.query(query, new Object[]{userName}, (rs, rowNum) ->
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
                           // rs.getString("CONTACT_EMAIL"),
                            rs.getString("OPERATORID")
                            //ID, PROFILE_ID, USERNAME, PASSWORD, USER_TYPE, ROLE_ID, IS_ACCOUNT_ACTIVE,
                            // IS_REMOTE_ACCESS_ENABLED, OPERATORID, TOKEN, TOKEN_EXPIRE, CREATED_AT, CREATED_BY,
                            // UPDATED_AT, UPDATED_BY, PARENT_PROFILE_ID, MAIN_ACCOUNT_ID
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
