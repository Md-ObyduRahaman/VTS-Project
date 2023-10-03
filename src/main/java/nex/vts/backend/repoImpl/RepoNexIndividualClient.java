package nex.vts.backend.repoImpl;

import nex.vts.backend.dbentities.NEX_INDIVIDUAL_CLIENT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class RepoNexIndividualClient {
    private final Logger logger = LoggerFactory.getLogger(RepoNexIndividualClient.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Optional<NEX_INDIVIDUAL_CLIENT> getParentProfileIdOfIndividualClient(int profileId) {
        logger.debug("Executing query to get parent profile id of individual client {}", profileId);
        return jdbcTemplate.queryForObject("SELECT COMPANY_ID AS PARENT_PROFILE_ID FROM GPSNEXGP.NEX_INDIVIDUAL_CLIENT WHERE ID = ?", new Object[]{profileId}, (rs, rowNum) ->
                Optional.of(new NEX_INDIVIDUAL_CLIENT(
                        rs.getInt("PARENT_PROFILE_ID")
                ))
        );
    }
}
