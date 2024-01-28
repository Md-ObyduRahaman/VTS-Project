package nex.vts.backend.repoImpl;

import nex.vts.backend.exceptions.AppCommonException;
import nex.vts.backend.models.responses.VehicleDetailInfo;
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
    public VehicleDetailInfo getVehicleDetailForGpAndM2M( Integer vehicleId, String schemaName) {

        String query = "select v.ID               VEHICLEID,\n" +
                "       v.CAR_NO                  CAR_NO,\n"+
                "       v.USERID                  USERID,\n" +
                "       v.FULL_NAME               FULL_NAME,\n" +
                "       v.CELL_PHONE              CELL_PHONE,\n" +
                "       v.CAR_COLOUR              CAR_COLOUR,\n" +
                "       v.CAR_VENDOR              CAR_VENDOR,\n" +
                "       v.CAR_MODEL               CAR_MODEL,\n" +
                /*"       v.CUSTOM_USERID           CUSTOM_USERID,\n" +*/
                "       v.EMAIL                   EMAIL,\n" +
                "       v.ICON_TYPE               ICON_TYPE,\n" +
                "       v.ICON_TYPE_ON_MAP        ICON_TYPE_ON_MAP,\n" +
                "       v.ICON_TYPE_RUNNING       ICON_TYPE_RUNNING,\n" +
                "       v.ICON_TYPE_STOPPED       ICON_TYPE_STOPPED,\n" +
                "       v.ICON_TYPE_STATIONARY    ICON_TYPE_STATIONARY,\n" +
                "       v.ICON_TYPE_OVERSPEED     ICON_TYPE_OVERSPEED,\n" +
                "       v.VEHICLE_IMAGE           VEHICLE_IMAGE,\n" +
                "       d.ID                      DRIVER_ID,\n" +
                "       d.D_NAME                  D_NAME,\n" +
                "       d.D_FNAME                 D_FNAME,\n" +
                "       d.D_LICENSE               D_LICENSE,\n" +
                "       d.D_ADDRESS               D_ADDRESS,\n" +
                "       d.D_CELL                  D_CELL,\n" +
                "       d.MAX_CAR_SPEED           MAX_CAR_SPEED,\n" +
                "       LENGTH(d.DRIVER_PHOTO) as DRIVER_PHOTO,\n" +
                "       d.CAR_IMAGE\n" +
                "FROM NEX_INDIVIDUAL_CLIENT v\n" +
                "         LEFT JOIN NEX_DRIVERINFO d ON v.ID = d.USERID\n" +
                "WHERE v.ID = ?";

        try{
            return jdbcTemplate.queryForObject(query, new Object[]{vehicleId}, new RowMapper<VehicleDetailInfo>() {
                @Override
                public VehicleDetailInfo mapRow(ResultSet rs, int rowNum) throws SQLException {

                    return new VehicleDetailInfo(

                            rs.getInt("VEHICLEID"),
                            rs.getString("CAR_NO"),
                            rs.getString("USERID"),
                            rs.getString("FULL_NAME"),
                            rs.getString("CELL_PHONE"),
                            rs.getString("EMAIL"),
                            rs.getString("CAR_COLOUR"),
                            rs.getString("CAR_VENDOR"),
                            rs.getString("CAR_MODEL"),
                           /* rs.getString("CUSTOM_USERID"),*/
                            rs.getInt("ICON_TYPE"),
                            rs.getInt("ICON_TYPE_ON_MAP"),
                            rs.getInt("ICON_TYPE_RUNNING"),
                            rs.getInt("ICON_TYPE_STOPPED"),
                            rs.getInt("ICON_TYPE_STATIONARY"),
                            rs.getInt("ICON_TYPE_OVERSPEED"),
                            rs.getBlob("VEHICLE_IMAGE"),
                            rs.getInt("DRIVER_ID"),
                            rs.getString("D_NAME"),
                            rs.getString("D_FNAME"),
                            rs.getString("D_LICENSE"),
                            rs.getString("D_ADDRESS"),
                            rs.getString("D_CELL"),
                            rs.getInt("MAX_CAR_SPEED"),
                            rs.getString("DRIVER_PHOTO"),
                            rs.getString("CAR_IMAGE")

                    );
                }
            });

        }catch (Exception e){

            logger.error(e.getMessage());
            throw new AppCommonException(e.getMessage());
        }
    }

}