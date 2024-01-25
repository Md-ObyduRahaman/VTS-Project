package nex.vts.backend.repoImpl;

import nex.vts.backend.exceptions.AppCommonException;
import nex.vts.backend.models.responses.VehicleDetailInfo;
import nex.vts.backend.models.vehicle.rowMapper.Vehicle_Details_RowMapper;
import nex.vts.backend.repositories.VehicleDetails_Repo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class VehDetails_RepoImp implements VehicleDetails_Repo {
    private final static Logger logger = LoggerFactory.getLogger(VehDetails_RepoImp.class);
    private final JdbcTemplate jdbcTemplate;

    public VehDetails_RepoImp(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public VehicleDetailInfo get_VehicleDetail_For_GpAndM2M(Integer userType, Integer vehRowId, String schemaName) {

//        try {
//            String query = "select " +
//                                "v.ID as VEH_ID, v.USERID, v.FULL_NAME," +
//                                "v.CELL_PHONE, v.CAR_COLOUR, v.CAR_VENDOR, v.CAR_MODEL, " +
//                                "v.CUSTOM_USERID, v.EMAIL, v.ICON_TYPE," +
//                                "v.ICON_TYPE_ON_MAP, v.ICON_TYPE_RUNNING, v.ICON_TYPE_STOPPED," +
//                                "v.ICON_TYPE_STATIONARY, v.ICON_TYPE_OVERSPEED," +
//                                "v.VEHICLE_IMAGE, " +
//                                "d.ID as DRIVER_ID, d.D_NAME, d.D_FNAME, d.D_LICENSE, d.D_ADDRESS, d.D_CELL, " +
//                                "d.MAX_CAR_SPEED, d.DRIVER_PHOTO, d.CAR_IMAGE,
//                    concat(schemaName).concat("GET_VEHICLE_OPTIONS(?, ?) VEHICLE_OPTIONS
//                    FROM NEX_INDIVIDUAL_CLIENT v
//                    LEFT JOIN NEX_DRIVERINFO d ON v.ID = d.USERID
//                    WHERE v.ID = 17215";
//
////String query = "select " +
////                            "v.ID ID," +
////                    "v.ICON_TYPE," +
////                    "v.ICON_TYPE_ON_MAP, v.ICON_TYPE_RUNNING," +
////                    "       V.ICON_TYPE_STOPPED      ICON_TYPE_STOPPED,\n" +
////                    "       V.ICON_TYPE_STATIONARY   ICON_TYPE_STATIONARY,\n" +
////                    "       V.ICON_TYPE_OVERSPEED    ICON_TYPE_OVERSPEED,\n" +
////                    "       V.USERID                 USERID,\n" +
////                    "       V.FULL_NAME              FULL_NAME,\n" +
////                    "       V.CELL_PHONE             CELL_PHONE,\n" +
////                    "       V.CAR_COLOUR             CAR_COLOUR,\n" +
////                    "       V.CAR_VENDOR             CAR_VENDOR,\n" +
////                    "       V.CAR_MODEL              CAR_MODEL,\n" +
////                    "       V.CUSTOM_USERID          CUSTOM_USERID,\n" +
////                    "       EMAIL                    EMAIL,\n" +
////                    "       LENGTH(V.VEHICLE_IMAGE)       VEHICLE_IMAGE,\n" +
////                    "       D.USERID                      DRIVER_ID,\n" +
////                    "       D.D_NAME                      D_NAME,\n" +
////                    "       D.D_FNAME                     D_FNAME,\n" +
////                    "       D.D_LICENSE                   D_LICENSE,\n" +
////                    "       D.D_ADDRESS                   D_ADDRESS,\n" +
////                    "       D.D_CELL                      D_CELL,\n" +
////                    "       D.MAX_CAR_SPEED               MAX_CAR_SPEED,\n" +
////                    "       LENGTH(D.DRIVER_PHOTO) as     DRIVER_PHOTO,\n" +
////                    "       D.CAR_IMAGE                   CAR_IMAGE,\n"
////                            .concat(schemaName).concat("GET_VEHICLE_OPTIONS(?, ?) VEHICLE_OPTIONS\n" +
////                                    "FROM NEX_INDIVIDUAL_CLIENT V\n" +
////                                    "         LEFT JOIN NEX_DRIVERINFO D ON V.ID = D.USERID\n" +
////                                    "WHERE V.ID = ?");
//
//            return jdbcTemplate.queryForObject(query,new Vehicle_Details_RowMapper() ,new Object[]{userType, vehRowId, profileId});
//
//        }catch (Exception e){
//            logger.error(e.getMessage());
//            throw new AppCommonException(e.getMessage());
//        }

return null;
//        return jdbcTemplate.queryForObject(getQuery, new Vehicle_Details_RowMapper(),new Object[]{userType,profileId,profileId});
    }

}
