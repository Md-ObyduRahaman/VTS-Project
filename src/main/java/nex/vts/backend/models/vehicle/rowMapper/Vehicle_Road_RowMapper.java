package nex.vts.backend.models.vehicle.RowMapper;

import nex.vts.backend.models.vehicle.Vehicle_Road;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Vehicle_Road_RowMapper implements RowMapper<Vehicle_Road> {
    @Override
    public Vehicle_Road mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Vehicle_Road(
                rs.getInt("ID"),
                rs.getInt("DIST_ID"),
                rs.getInt("THANA_ID"),
                rs.getString("DESCRIPTION"),
                rs.getString("POLYX"),
                rs.getString("POLYY"),
                rs.getString("DESCRIPTION_B")
        );
    }
}
