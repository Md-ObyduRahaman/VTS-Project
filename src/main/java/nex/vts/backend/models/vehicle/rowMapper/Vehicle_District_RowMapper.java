package nex.vts.backend.models.vehicle.RowMapper;

import nex.vts.backend.models.vehicle.Vehicle_District;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Vehicle_District_RowMapper implements RowMapper<Vehicle_District> {
    @Override
    public Vehicle_District mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Vehicle_District(
                rs.getInt("ID"),
                rs.getString("DESCRIPTION"),
                rs.getString("POLYX"),
                rs.getString("POLYY"),
                rs.getString("DESCRIPTION_B")
        );
    }
}
