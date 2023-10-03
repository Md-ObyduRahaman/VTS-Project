package nex.vts.backend.repoImpl;

import nex.vts.backend.dbentities.NEX_CORPORATE_CLIENT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class RepoNexCorporateClient {
    private final Logger logger = LoggerFactory.getLogger(RepoNexCorporateClient.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Optional<NEX_CORPORATE_CLIENT> getCorporateClientProfile(int profileId) {
        logger.debug("Executing query to get client profile by client profile id: {}", profileId);
        return jdbcTemplate.queryForObject("SELECT USER_ID, CLIENT_TYPE, COMPANY_NAME, COMPANY_ADDRESS, ADDRESS2, COMPANY_CELL, COMPANY_TEL, COMPANY_EMAIL FROM GPSNEXGP.NEX_CORPORATE_CLIENT WHERE ID = ?", new Object[]{profileId}, (rs, rowNum) ->
                Optional.of(new NEX_CORPORATE_CLIENT(
                        rs.getString("USER_ID"),
                        rs.getString("CLIENT_TYPE"),
                        rs.getString("COMPANY_NAME"),
                        rs.getString("COMPANY_ADDRESS"),
                        rs.getString("ADDRESS2"),
                        rs.getString("COMPANY_CELL"),
                        rs.getString("COMPANY_TEL"),
                        rs.getString("COMPANY_EMAIL")
                ))
        );
    }
}
