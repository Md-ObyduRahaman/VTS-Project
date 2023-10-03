package nex.vts.backend.models.vehicle.rowMapper;

import nex.vts.backend.models.vehicle.Vehicle_Details;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Vehicle_Details_RowMapper implements RowMapper<Vehicle_Details> {
    @Override
    public Vehicle_Details mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Vehicle_Details(
                rs.getInt("ICON_TYPE"),
                rs.getInt("ICON_TYPE_ON_MAP"),
                rs.getInt("ICON_TYPE_RUNNING"),
                rs.getInt("ICON_TYPE_STOPPED"),
                rs.getInt("ICON_TYPE_STATIONARY"),
                rs.getString("vehicle_name"),
                rs.getString("registration_number"),
                rs.getFloat("SPEED"),
                rs.getString("engin_status"),
                rs.getInt("is_favorite"),
                rs.getString("color"),
                rs.getString("vendor"),
                rs.getString("model"),
                rs.getString("CUSTOM_USERID"),
                rs.getInt("VEHICLE_IMAGE"),
                rs.getString("DRIVER_ID"),
                rs.getString("driver_name"),
                rs.getString("driver_cell"),
                rs.getString("MAX_CAR_SPEED"),
                rs.getInt("DRIVER_PHOTO"),
                rs.getString("CAR_IMAGE"),
                rs.getString("VEHICLE_OPTIONS")
        );
    }
}
