package nex.vts.backend.models.vehicle.rowMapper;

import nex.vts.backend.models.vehicle.Vehicle_Location;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Vehicle_Location_RowMapper implements RowMapper<Vehicle_Location> {
    @Override
    public Vehicle_Location mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Vehicle_Location(
                rs.getString("vehicle_id"),
                rs.getString("vehicle_name"),
                rs.getFloat("latitude"),
                rs.getFloat("longitude"),
                rs.getString("vehicle_time"),
                rs.getString("engine"),
                rs.getFloat("speed")
        );
    }
}
