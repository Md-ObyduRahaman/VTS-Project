package nex.vts.backend.repoImpl;

import nex.vts.backend.models.responses.VehicleDetails;
import nex.vts.backend.models.vehicle.rowMapper.Vehicle_Details_RowMapper;
import nex.vts.backend.repositories.Vehicle_Details_Repo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

@Repository
public class Vehicle_DetailsPermission_Repo_Imp implements Vehicle_Details_Repo {
    private final static Logger LOGGER = LoggerFactory.getLogger(Vehicle_DetailsPermission_Repo_Imp.class);
    private final JdbcTemplate jdbcTemplate;

    public Vehicle_DetailsPermission_Repo_Imp(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public VehicleDetails getVehicleDetails(Integer userType, Integer profileId,String schemaName) throws SQLException {
        String getQuery = "SELECT V.ICON_TYPE                        ICON_TYPE,\n" +
                "       V.ICON_TYPE_ON_MAP                 ICON_TYPE_ON_MAP,\n" +
                "       V.ICON_TYPE_RUNNING                ICON_TYPE_RUNNING,\n" +
                "       V.ICON_TYPE_STOPPED                ICON_TYPE_STOPPED,\n" +
                "       V.ICON_TYPE_STATIONARY             ICON_TYPE_STATIONARY,\n" +
                "       V.USERID                           vehicle_name,\n" +
                "       V.CAR_REG_NO                       registration_number,\n" +
                "       V.SPEED                            SPEED,\n" +
                "       V.ENGIN                            engin_status,\n" +
                "       V.FAVORITE                         is_favorite,\n" +
                "       V.CAR_COLOUR                       color,\n" +
                "       V.CAR_VENDOR                       vendor,\n" +
                "       V.CAR_MODEL                        model,\n" +
                "       V.CUSTOM_USERID                    CUSTOM_USERID,\n" +
                "       LENGTH(V.VEHICLE_IMAGE)            VEHICLE_IMAGE,\n" +
                "       D.USERID                           DRIVER_ID,\n" +
                "       D.D_NAME                           driver_name,\n" +
                "       D.D_CELL                           driver_cell,\n" +
                "       D.MAX_CAR_SPEED                    MAX_CAR_SPEED,\n" +
                "git        LENGTH(D.DRIVER_PHOTO)             DRIVER_PHOTO,\n" +
                "       D.CAR_IMAGE                        CAR_IMAGE,".concat(schemaName)
                        .concat("GET_VEHICLE_OPTIONS(?, ?) VEHICLE_OPTIONS\n" +
                                "FROM ").concat(schemaName).concat("NEX_INDIVIDUAL_CLIENT V\n" +
                                "         LEFT JOIN ").concat(schemaName).concat("NEX_DRIVERINFO D ON V.ID = D.USERID\n" +
                                "WHERE V.ID = ?");
        return jdbcTemplate.queryForObject(getQuery, new Vehicle_Details_RowMapper(),new Object[]{userType,profileId,profileId});
    }

}
