package nex.vts.backend.models.vehicle.rowMapper;

import nex.vts.backend.models.responses.VehicleDetails;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Vehicle_Details_RowMapper implements RowMapper<VehicleDetails> {
    @Override
    public VehicleDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new VehicleDetails(
                rs.getString("DRIVER_ID"),
                rs.getInt("ICON_TYPE"),
                rs.getString("vendor"),
                rs.getInt("ICON_TYPE_STOPPED"),
                rs.getString("vehicle_name"),
                rs.getString("model"),
                rs.getString("CAR_IMAGE"),
                rs.getString("VEHICLE_OPTIONS"),
                rs.getFloat("SPEED"),
                rs.getInt("ICON_TYPE_RUNNING"),
                rs.getString("registration_number"),
                rs.getString("driver_cell"),
                rs.getString("CUSTOM_USERID"),
                rs.getString("engin_status"),
                rs.getInt("ICON_TYPE_STATIONARY"),
                rs.getString("color"),
                rs.getString("driver_name"),
                rs.getInt("ICON_TYPE_ON_MAP"),
                rs.getString("MAX_CAR_SPEED")
        );
    }
}
