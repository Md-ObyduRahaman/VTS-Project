package nex.vts.backend.models.vehicle.rowMapper;

import nex.vts.backend.models.vehicle.Vehicle_History_UserInput;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Vehicle_History_UserInput_RowMapper implements RowMapper<Vehicle_History_UserInput> {
    @Override
    public Vehicle_History_UserInput mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Vehicle_History_UserInput();
    }
}
