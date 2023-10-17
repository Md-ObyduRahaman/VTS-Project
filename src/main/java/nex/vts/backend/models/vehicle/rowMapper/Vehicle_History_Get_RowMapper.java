package nex.vts.backend.models.vehicle.rowMapper;

import nex.vts.backend.models.vehicle.Vehicle_History_Get;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Vehicle_History_Get_RowMapper implements RowMapper<Vehicle_History_Get> {
    @Override
    public Vehicle_History_Get mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Vehicle_History_Get(
                rs.getString("max_speed"),
                rs.getLong("rowno"),
                rs.getLong("id"),
                rs.getString("vehicle_id"),
                rs.getString("group_id"),
                rs.getString("device_id"),
                rs.getString("time_stamp"),
                rs.getDouble("latitude"),
                rs.getDouble("longitude"),
                rs.getLong("date_time"),
                rs.getString("engine_status"),
                rs.getString("speed")

        );
    }
}
