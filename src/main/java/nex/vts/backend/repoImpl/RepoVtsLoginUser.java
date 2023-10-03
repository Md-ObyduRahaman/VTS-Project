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

    public Optional<VTS_LOGIN_USER> findByUserName(String userName) {

        logger.trace("Executing query to find user by username: {}", userName);
        Optional<VTS_LOGIN_USER> userObj = Optional.empty();
        String query = "SELECT *\n" +
                "FROM (SELECT *\n" +
                "      FROM GPSNEXGP.VTS_LOGIN_USER\n" +
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
                            rs.getString("PASSWORD")
                    )
            ).stream().findFirst();
        } catch (Exception e) {
            if (e instanceof EmptyResultDataAccessException) {
                logger.trace("No user found with username {} on VTS_LOGIN_USER tbl", userName);
                return userObj;
            }
        }

        return userObj;
    }

}
