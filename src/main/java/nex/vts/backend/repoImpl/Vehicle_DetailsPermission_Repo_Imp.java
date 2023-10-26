package nex.vts.backend.repoImpl;

import nex.vts.backend.models.vehicle.rowMapper.Vehicle_Details_RowMapper;
import nex.vts.backend.models.vehicle.rowMapper.Vehicle_Permission_RowMapper;
import nex.vts.backend.repositories.Vehicle_Details_Repo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;

@Repository
public class Vehicle_DetailsPermission_Repo_Imp implements Vehicle_Details_Repo {
    private final static Logger LOGGER = LoggerFactory.getLogger(Vehicle_DetailsPermission_Repo_Imp.class);
    private final JdbcTemplate jdbcTemplate;

    public Vehicle_DetailsPermission_Repo_Imp(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Object getVehicleDetails(Integer userType, Integer profileId, Integer vehicleId) throws SQLException {
        String getQuery = "SELECT V.ICON_TYPE  ICON_TYPE, V.ICON_TYPE_ON_MAP   ICON_TYPE_ON_MAP, V.ICON_TYPE_RUNNING  ICON_TYPE_RUNNING,  V.ICON_TYPE_STOPPED   ICON_TYPE_STOPPED, V.ICON_TYPE_STATIONARY ICON_TYPE_STATIONARY,   V.USERID  vehicle_name, V.CAR_REG_NO   registration_number,  V.SPEED    SPEED,   V.ENGIN    engin_status,   V.FAVORITE  is_favorite,   V.CAR_COLOUR    color,   V.CAR_VENDOR    vendor,   V.CAR_MODEL     model,  V.CUSTOM_USERID  CUSTOM_USERID,  LENGTH(V.VEHICLE_IMAGE)   VEHICLE_IMAGE,   D.USERID  DRIVER_ID,  D.D_NAME   driver_name,  D.D_CELL   driver_cell,   D.MAX_CAR_SPEED    MAX_CAR_SPEED, LENGTH(D.DRIVER_PHOTO)   DRIVER_PHOTO,  D.CAR_IMAGE   CAR_IMAGE,   GPSNEXGP.GET_VEHICLE_OPTIONS(?, ?) VEHICLE_OPTIONS FROM NEX_INDIVIDUAL_CLIENT V   LEFT JOIN NEX_DRIVERINFO D ON V.ID = D.USERID WHERE V.ID = ?";
        return jdbcTemplate.query(getQuery, new Vehicle_Details_RowMapper(), userType, profileId, vehicleId);
    }

    @Override
    public Object getVehiclePermission(Integer userType, Integer profileId, Integer parentId, Integer vehicleId) {
        Integer userType2 = userType, userType3 = userType, profileId2 = profileId, profileId3 = profileId, parentId2 = parentId, parentId3 = parentId;
        String getQuery = "SELECT B.EMAIL EMAIL,B.SMS   SMS,  A.IS_SAFE_MODE_ACTIVE  IS_SAFE_MODE_ACTIVE,  A.IS_MULTIPLE_N_ALLOW   IS_MULTIPLE_N_ALLOW, C.MAX_CAR_SPEED   MAX_CAR_SPEED, check_veh_profile_permission('ChangeSMS', ?, ?, ?, A.ID) IsChangeSMS, check_veh_profile_permission('ChangeEmail', ?, ?, ?, A.ID) IsChangeEmail, check_veh_profile_permission('ChangeMaxSpeed', ?, ?, ?, A.ID) IsChangeMaxSpeed,  1     IsIndGeoEnable,   1   IsIndSpdEnable FROM GPSNEXGP.NEX_INDIVIDUAL_CLIENT A,  GPSNEXGP.NEX_IND_ALERT_OPTION B,  GPSNEXGP.NEX_DRIVERINFO C WHERE A.ID = B.VEHICLE_ID AND A.ID = C.USERID AND A.ID = ?";
        try {
            return jdbcTemplate.query(getQuery, new Vehicle_Permission_RowMapper(), userType, profileId, parentId, userType2, profileId2, parentId2, userType3, profileId3, parentId3, vehicleId);
        } catch (BadSqlGrammarException e) {
            return "Uncategorized Exception, Provide proper parameter ";
        } catch (DataAccessException accessException) {
            return accessException.getMessage();
        }
    }
}
