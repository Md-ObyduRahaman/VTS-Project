package nex.vts.backend.repoImpl;

import nex.vts.backend.exceptions.AppCommonException;
import nex.vts.backend.models.responses.VehicleDetailInfo;
import nex.vts.backend.models.vehicle.rowMapper.Vehicle_Details_RowMapper;
import nex.vts.backend.repositories.VehicleDetails_Repo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class VehicleDetailRepo_Imp implements VehicleDetails_Repo {
    private final static Logger logger = LoggerFactory.getLogger(VehicleDetailRepo_Imp.class);
    private final JdbcTemplate jdbcTemplate;

    public VehicleDetailRepo_Imp(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public VehicleDetailInfo getVehicleDetailForGpAndM2M(Integer userType, Integer profileId, String schemaName) {

        try{
            String query = "SELECT V.ID                     ID,\n" +
                    "       V.ICON_TYPE              ICON_TYPE,\n" +
                    "       V.ICON_TYPE_ON_MAP       ICON_TYPE_ON_MAP,\n" +
                    "       V.ICON_TYPE_RUNNING      ICON_TYPE_RUNNING,\n" +
                    "       V.ICON_TYPE_STOPPED      ICON_TYPE_STOPPED,\n" +
                    "       V.ICON_TYPE_STATIONARY   ICON_TYPE_STATIONARY,\n" +
                    "       V.ICON_TYPE_OVERSPEED    ICON_TYPE_OVERSPEED,\n" +
                    "       V.USERID                 USERID,\n" +
                    "       V.FULL_NAME              FULL_NAME,\n" +
                    "       V.CELL_PHONE             CELL_PHONE,\n" +
                    "       V.CAR_COLOUR             CAR_COLOUR,\n" +
                    "       V.CAR_VENDOR             CAR_VENDOR,\n" +
                    "       V.CAR_MODEL              CAR_MODEL,\n" +
                    "       V.CUSTOM_USERID          CUSTOM_USERID,\n" +
                    "       EMAIL                    EMAIL,\n" +
                    "       LENGTH(V.VEHICLE_IMAGE)       VEHICLE_IMAGE,\n" +
                    "       D.USERID                      DRIVER_ID,\n" +
                    "       D.D_NAME                      D_NAME,\n" +
                    "       D.D_FNAME                     D_FNAME,\n" +
                    "       D.D_LICENSE                   D_LICENSE,\n" +
                    "       D.D_ADDRESS                   D_ADDRESS,\n" +
                    "       D.D_CELL                      D_CELL,\n" +
                    "       D.MAX_CAR_SPEED               MAX_CAR_SPEED,\n" +
                    "       LENGTH(D.DRIVER_PHOTO) as     DRIVER_PHOTO,\n" +
                    "       D.CAR_IMAGE                   CAR_IMAGE,\n"
                            .concat(schemaName).concat("GET_VEHICLE_OPTIONS(?, ?) VEHICLE_OPTIONS\n" +
                                    "FROM NEX_INDIVIDUAL_CLIENT V\n" +
                                    "         LEFT JOIN NEX_DRIVERINFO D ON V.ID = D.USERID\n" +
                                    "WHERE V.ID = ?");

            return jdbcTemplate.queryForObject(query,new Vehicle_Details_RowMapper() ,new Object[]{userType, profileId, profileId});

        }catch (Exception e){

            logger.error(e.getMessage());
            throw new AppCommonException(e.getMessage());
        }

        //        String getQuery = "SELECT V.ICON_TYPE                        ICON_TYPE,\n" +
//                "       V.ICON_TYPE_ON_MAP                 ICON_TYPE_ON_MAP,\n" +
//                "       V.ICON_TYPE_RUNNING                ICON_TYPE_RUNNING,\n" +
//                "       V.ICON_TYPE_STOPPED                ICON_TYPE_STOPPED,\n" +
//                "       V.ICON_TYPE_STATIONARY             ICON_TYPE_STATIONARY,\n" +
//                "       V.USERID                           vehicle_name,\n" +
//                "       V.CAR_REG_NO                       registration_number,\n" +
//                "       V.SPEED                            SPEED,\n" +
//                "       V.ENGIN                            engin_status,\n" +
//                "       V.FAVORITE                         is_favorite,\n" +
//                "       V.CAR_COLOUR                       color,\n" +
//                "       V.CAR_VENDOR                       vendor,\n" +
//                "       V.CAR_MODEL                        model,\n" +
//                "       V.CUSTOM_USERID                    CUSTOM_USERID,\n" +
//                "       LENGTH(V.VEHICLE_IMAGE)            VEHICLE_IMAGE,\n" +
//                "       D.USERID                           DRIVER_ID,\n" +
//                "       D.D_NAME                           driver_name,\n" +
//                "       D.D_CELL                           driver_cell,\n" +
//                "       D.MAX_CAR_SPEED                    MAX_CAR_SPEED,\n" +
//                "       LENGTH(D.DRIVER_PHOTO)             DRIVER_PHOTO,\n" +
//                "       D.CAR_IMAGE                        CAR_IMAGE,".concat(schemaName)
//                        .concat("GET_VEHICLE_OPTIONS(?, ?) VEHICLE_OPTIONS\n" +
//                                "FROM ").concat(schemaName).concat("NEX_INDIVIDUAL_CLIENT V\n" +
//                                "         LEFT JOIN ").concat(schemaName).concat("NEX_DRIVERINFO D ON V.ID = D.USERID\n" +
//                                "WHERE V.ID = ?");
//        return jdbcTemplate.queryForObject(getQuery, new Vehicle_Details_RowMapper(),new Object[]{userType,profileId,profileId});
    }

}
