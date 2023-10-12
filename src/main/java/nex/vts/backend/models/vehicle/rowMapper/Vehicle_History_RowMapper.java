package nex.vts.backend.models.vehicle.rowMapper;

import nex.vts.backend.models.vehicle.Vehicle_History;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Vehicle_History_RowMapper implements RowMapper<Vehicle_History> {
    @Override
    public Vehicle_History mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Vehicle_History();
    }
}
