package nex.vts.backend.repoImpl;

import nex.vts.backend.models.vehicle.rowMapper.Vehicle_List_RowMapper;
import nex.vts.backend.models.vehicle.rowMapper.*;
import nex.vts.backend.repositories.Vehicle_Details_Repo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;

@Repository
@SuppressWarnings("all")
public class Vehicle_Details_Permission_Repo_Imp implements Vehicle_Details_Repo {
    private final static Logger LOGGER = LoggerFactory.getLogger(Vehicle_Details_Permission_Repo_Imp.class);
    private final JdbcTemplate jdbcTemplate;

    public Vehicle_Details_Permission_Repo_Imp(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Object getVehicleDetails(Integer userType, Integer profileId, Integer vehicleId) throws SQLException {
        String getQuery = "SELECT V.ICON_TYPE                          ICON_TYPE,\n" + "       V.ICON_TYPE_ON_MAP                   ICON_TYPE_ON_MAP,\n" + "       V.ICON_TYPE_RUNNING                  ICON_TYPE_RUNNING,\n" + "       V.ICON_TYPE_STOPPED                  ICON_TYPE_STOPPED,\n" + "       V.ICON_TYPE_STATIONARY               ICON_TYPE_STATIONARY,\n" + "       V.USERID                             vehicle_name,\n" + "       V.CAR_REG_NO                         registration_number,\n" + "       V.SPEED                              SPEED,\n" + "       V.ENGIN                              engin_status,\n" + "       V.FAVORITE                           is_favorite,\n" + "       V.CAR_COLOUR                         color,\n" + "       V.CAR_VENDOR                         vendor,\n" + "       V.CAR_MODEL                          model,\n" + "       V.CUSTOM_USERID                      CUSTOM_USERID,\n" + "       LENGTH(V.VEHICLE_IMAGE)              VEHICLE_IMAGE,\n" + "       D.USERID                             DRIVER_ID,\n" + "       D.D_NAME                             driver_name,\n" + "       D.D_CELL                             driver_cell,\n" + "       D.MAX_CAR_SPEED                      MAX_CAR_SPEED,\n" + "       LENGTH(D.DRIVER_PHOTO)               DRIVER_PHOTO,\n" + "       D.CAR_IMAGE                          CAR_IMAGE,\n" + "       GPSNEXGP.GET_VEHICLE_OPTIONS(?, ?) VEHICLE_OPTIONS\n" + "FROM NEX_INDIVIDUAL_CLIENT V\n" + "         LEFT JOIN NEX_DRIVERINFO D ON V.ID = D.USERID\n" + "WHERE V.ID = ?";
        return jdbcTemplate.query(getQuery, new Vehicle_Details_RowMapper(), userType, profileId, vehicleId);
    }

    @Override
    public Object getVehiclePermission(Integer userType, Integer profileId, Integer parentId, Integer vehicleId) {
        Integer userType2 = userType, userType3 = userType, profileId2 = profileId, profileId3 = profileId, parentId2 = parentId, parentId3 = parentId;
        String getQuery = "SELECT B.EMAIL                                                       EMAIL,\n" + "       B.SMS                                                         SMS,\n" + "       A.IS_SAFE_MODE_ACTIVE                                         IS_SAFE_MODE_ACTIVE,\n" + "       A.IS_MULTIPLE_N_ALLOW                                         IS_MULTIPLE_N_ALLOW,\n" + "       C.MAX_CAR_SPEED                                               MAX_CAR_SPEED,\n" + "       check_veh_profile_permission('ChangeSMS', ?, ?, ?, A.ID)      IsChangeSMS,\n" + "       check_veh_profile_permission('ChangeEmail', ?, ?, ?, A.ID)    IsChangeEmail,\n" + "       check_veh_profile_permission('ChangeMaxSpeed', ?, ?, ?, A.ID) IsChangeMaxSpeed,\n" + "       1                                                             IsIndGeoEnable,\n" + "       1                                                             IsIndSpdEnable\n" + "FROM GPSNEXGP.NEX_INDIVIDUAL_CLIENT A,\n" + "     GPSNEXGP.NEX_IND_ALERT_OPTION B,\n" + "     GPSNEXGP.NEX_DRIVERINFO C\n" + "WHERE A.ID = B.VEHICLE_ID\n" + "  AND A.ID = C.USERID\n" + "  AND A.ID = ?";
        try {
            return jdbcTemplate.query(getQuery, new Vehicle_Permission_RowMapper(), userType, profileId, parentId, userType2, profileId2, parentId2, userType3, profileId3, parentId3, vehicleId);
        } catch (BadSqlGrammarException e) {
            return "Uncatogorized Exception, Provide proper parameter ";
        } catch (DataAccessException accessException) {
            return accessException.getMessage();
        }
    }
}
