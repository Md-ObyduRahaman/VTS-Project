package nex.vts.backend.repoImpl;

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

    /*
    *$sql_Dept = "select * FROM NEX_VEHICLE_DEPT
                  where USERID = '$_UserName' and GRP_PASS = '$_UserPass' and ACTIVATION = 1
                  and OPERATORID = 3";
    * */

    public Optional<NEX_VEHICLE_DEPT> getParentProfileIdOfDepartmentClient(String userName, String password,Integer operatorid ) {
        logger.debug("Executing query to get parent userName  of department type client {}", userName);
        return jdbcTemplate.queryForObject("SELECT COMPANY_ID AS PARENT_PROFILE_ID FROM GPSNEXGP.NEX_VEHICLE_DEPT  where USERID = ? and GRP_PASS = ? and ACTIVATION = 1 and OPERATORID = ?", new Object[]{userName,password,operatorid}, (rs, rowNum) ->
                Optional.of(new NEX_VEHICLE_DEPT(
                        rs.getInt("PARENT_PROFILE_ID")
                ))
        );
    }
}
