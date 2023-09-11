package nex.vts.backend.repositories;

import nex.vts.backend.dbentities.VTS_EXTENDED_USER_PROFILE;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class RepoVtsExtendedUserProfile {
    private final Logger logger = LoggerFactory.getLogger(RepoVtsExtendedUserProfile.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Optional<VTS_EXTENDED_USER_PROFILE> getParentProfileIdOfExtendedClient(int profileId) {
        logger.debug("Executing query to get parent profile id of extended type client {}", profileId);
        return jdbcTemplate.queryForObject("SELECT PARENT_PROFILE_ID FROM GPSNEXGP.VTS_EXTENDED_USER_PROFILE WHERE ID = ?", new Object[]{profileId}, (rs, rowNum) ->
                Optional.of(new VTS_EXTENDED_USER_PROFILE(
                        rs.getInt("PARENT_PROFILE_ID")
                ))
        );
    }
}
