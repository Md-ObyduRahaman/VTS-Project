package nex.vts.backend.models.vehicle.rowMapper;

import nex.vts.backend.models.responses.VehicleDetailInfo;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Vehicle_Details_RowMapper implements RowMapper<VehicleDetailInfo> {
    @Override
    public VehicleDetailInfo mapRow(ResultSet rs, int rowNum) throws SQLException {

        return new VehicleDetailInfo(

                rs.getInt("ID"),
                rs.getInt("ICON_TYPE"),
                rs.getInt("ICON_TYPE_ON_MAP"),
                rs.getInt("ICON_TYPE_RUNNING"),
                rs.getInt("ICON_TYPE_STOPPED"),
                rs.getInt("ICON_TYPE_STATIONARY"),
                rs.getInt("ICON_TYPE_OVERSPEED"),
                rs.getString("USERID"),
                rs.getString("FULL_NAME"),
                rs.getString("CELL_PHONE"),
                rs.getString("CAR_COLOUR"),
                rs.getString("CAR_VENDOR"),
                rs.getString("CAR_MODEL"),
                rs.getString("CUSTOM_USERID"),
                rs.getString("EMAIL"),
                rs.getInt("VEHICLE_IMAGE"),
                rs.getString("DRIVER_ID"),
                rs.getString("D_NAME"),
                rs.getString("D_FNAME"),
                rs.getString("D_LICENSE"),
                rs.getString("D_ADDRESS"),
                rs.getString("D_CELL"),
                rs.getString("MAX_CAR_SPEED"),
                rs.getInt("DRIVER_PHOTO"),
                rs.getString("CAR_IMAGE"),
                rs.getString("VEHICLE_OPTIONS")
        );
    }
}
