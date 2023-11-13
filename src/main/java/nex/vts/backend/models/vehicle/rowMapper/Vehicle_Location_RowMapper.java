package nex.vts.backend.models.vehicle.rowMapper;

import nex.vts.backend.models.responses.VehicleLocation;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Vehicle_Location_RowMapper implements RowMapper<VehicleLocation> {
    @Override
    public VehicleLocation mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new VehicleLocation(
                rs.getString("vehicle_id"),
                rs.getString("engine"),
                rs.getString("vehicle_time"),
//                rs.getString("locationAddress"),
                rs.getString("vehicle_name"),
                rs.getFloat("latitude"),
                rs.getFloat("longitude"),
                rs.getFloat("speed")
        );
    }
}
