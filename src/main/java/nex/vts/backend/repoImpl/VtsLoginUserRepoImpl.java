package nex.vts.backend.repoImpl;

import nex.vts.backend.repositories.VtsLoginUserRepo;
import nex.vts.backend.dbentities.VTS_LOGIN_USER;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class VtsLoginUserRepoImpl implements VtsLoginUserRepo {

    private final Logger logger = LoggerFactory.getLogger(VtsLoginUserRepoImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Optional<VTS_LOGIN_USER> findByUserName(String userName) {
        logger.debug("Executing query to find user by username: {}", userName);
        return jdbcTemplate.queryForObject("SELECT * FROM VTS_LOGIN_USER where USERNAME = ?", new Object[]{userName}, (rs, rowNum) ->
                Optional.of(new VTS_LOGIN_USER(
                        rs.getLong("ID"),
                        rs.getLong("PROFILE_ID"),
                        rs.getLong("MAIN_ACCOUNT_ID"),
                        rs.getInt("USER_TYPE"),
                        rs.getInt("ROLE_ID"),
                        rs.getInt("IS_ACCOUNT_ACTIVE"),
                        rs.getInt("IS_REMOTE_ACCESS_ENABLED"),
                        rs.getString("USERNAME"),
                        rs.getString("PASSWORD")
                ))
        );
    }

}
