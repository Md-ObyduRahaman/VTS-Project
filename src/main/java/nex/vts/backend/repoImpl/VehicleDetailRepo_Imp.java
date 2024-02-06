package nex.vts.backend.repoImpl;

import nex.vts.backend.exceptions.AppCommonException;
import nex.vts.backend.models.responses.VehicleDetailInfo;
import nex.vts.backend.repositories.VehicleDetails_Repo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class VehicleDetailRepo_Imp implements VehicleDetails_Repo {
    private final static Logger logger = LoggerFactory.getLogger(VehicleDetailRepo_Imp.class);
    private final JdbcTemplate jdbcTemplate;

    private final static short API_VERSION = 1;

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
                /*"       v.VEHICLE_IMAGE           VEHICLE_IMAGE,\n" +*/
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

            VehicleDetailInfo detailInfo = jdbcTemplate.query(query, new Object[]{vehicleId},
                    new ResultSetExtractor<VehicleDetailInfo>() {
                @Override
                public VehicleDetailInfo extractData(ResultSet rs) throws SQLException, DataAccessException {

                    VehicleDetailInfo info = new VehicleDetailInfo();

                    while (rs.next()){

                                info.setVehicleId(rs.getInt("VEHICLEID"));
                                info.setCarNo(rs.getString("CAR_NO"));
                                info.setUserId(rs.getString("USERID"));
                                info.setFullName(rs.getString("FULL_NAME"));
                                info.setCellPhone(rs.getString("CELL_PHONE"));
                                info.setEmail(rs.getString("EMAIL"));
                                info.setCarColor(rs.getString("CAR_COLOUR"));
                                info.setCarVendor(rs.getString("CAR_VENDOR"));
                                info.setCarModel(rs.getString("CAR_MODEL"));
                                /* rs.getString("CUSTOM_USERID"),*/
                                info.setIconType(rs.getInt("ICON_TYPE"));
                                info.setIconTypeOnMap(rs.getInt("ICON_TYPE_ON_MAP"));
                                info.setIconTypeRunning(rs.getInt("ICON_TYPE_RUNNING"));
                                info.setIconTypeStopped(rs.getInt("ICON_TYPE_STOPPED"));
                                info.setIconTypeStationary(rs.getInt("ICON_TYPE_STATIONARY"));
                                info.setIconTypeOverSpeed(rs.getInt("ICON_TYPE_OVERSPEED"));
                                /*String.valueOf(rs.getBinaryStream("VEHICLE_IMAGE")),*/
                                info.setDriverId(rs.getInt("DRIVER_ID"));
                                info.setDName(rs.getString("D_NAME"));
                                info.setFName(rs.getString("D_FNAME"));
                                info.setDLicense(rs.getString("D_LICENSE"));
                                info.setDAddress(rs.getString("D_ADDRESS"));
                                info.setDCell(rs.getString("D_CELL"));
                                info.setMaxCarSpeed(rs.getInt("MAX_CAR_SPEED"));
                                info.setDriverPhoto(rs.getString("DRIVER_PHOTO"));
                                info.setCarImage(rs.getString("CAR_IMAGE"));

                    }
                    return info;
                }
            });

            return detailInfo;

        }catch (Exception e){

            logger.error(e.getMessage());
            throw new AppCommonException(403 + "##Vehicle details is empty ##"+ vehicleId + "##" + API_VERSION);
        }
    }

}