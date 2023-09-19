package nex.vts.backend.repositories;

import nex.vts.backend.dbentities.NEX_VEHICLE_DEPT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class RepoNexVehicleDept {
    private final Logger logger = LoggerFactory.getLogger(RepoNexVehicleDept.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Optional<NEX_VEHICLE_DEPT> getParentProfileIdOfDepartmentClient(int profileId) {
        logger.debug("Executing query to get parent profile id of department type client {}", profileId);
        return jdbcTemplate.queryForObject("SELECT COMPANY_ID AS PARENT_PROFILE_ID FROM GPSNEXGP.NEX_VEHICLE_DEPT WHERE ID = ?", new Object[]{profileId}, (rs, rowNum) ->
                Optional.of(new NEX_VEHICLE_DEPT(
                        rs.getInt("PARENT_PROFILE_ID")
                ))
        );
    }
}
