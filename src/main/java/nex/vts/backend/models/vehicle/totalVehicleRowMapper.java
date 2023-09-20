package nex.vts.backend.models.vehicle;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class totalVehicleRowMapper implements RowMapper<totalVehicle> {
    @Override
    public totalVehicle mapRow(ResultSet rs, int rowNum) throws SQLException {
        return  new totalVehicle(
                 rs.getInt("total")
        );
    }
}
