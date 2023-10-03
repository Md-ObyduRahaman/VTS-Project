package nex.vts.backend.models.vehicle.rowMapper;

import nex.vts.backend.models.vehicle.Vehicle_Thana;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Vehicle_Thana_RowMapper implements RowMapper<Vehicle_Thana> {
    @Override
    public Vehicle_Thana mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Vehicle_Thana(
                rs.getInt("ID"),
                rs.getInt("DIST_ID"),
                rs.getString("DESCRIPTION"),
                rs.getString("POLYX"),
                rs.getString("POLYY"),
                rs.getString("DESCRIPTION_B")
        );
    }
}
