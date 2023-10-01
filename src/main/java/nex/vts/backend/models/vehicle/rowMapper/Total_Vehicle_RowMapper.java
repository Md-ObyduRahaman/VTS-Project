package nex.vts.backend.models.vehicle.RowMapper;

import nex.vts.backend.models.vehicle.Total_Vehicle;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Total_Vehicle_RowMapper implements RowMapper<Total_Vehicle> {
    @Override
    public Total_Vehicle mapRow(ResultSet rs, int rowNum) throws SQLException {
        return  new Total_Vehicle(
                 rs.getInt("total")
        );
    }
}
