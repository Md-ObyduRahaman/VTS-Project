package nex.vts.backend.models.vehicle.rowMapper;

import nex.vts.backend.models.vehicle.Vehicle_Permission;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Vehicle_Permission_RowMapper implements RowMapper<Vehicle_Permission> {
    @Override
    public Vehicle_Permission mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Vehicle_Permission(
                rs.getInt("EMAIL"),
                rs.getInt("SMS"),
                rs.getInt("IS_SAFE_MODE_ACTIVE"),
                rs.getInt("IS_MULTIPLE_N_ALLOW"),
                rs.getString("MAX_CAR_SPEED"),
                rs.getInt("IsChangeSMS"),
                rs.getInt("IsChangeEmail"),
                rs.getInt("IsChangeMaxSpeed"),
                rs.getInt("IsIndGeoEnable"),
                rs.getInt("IsIndSpdEnable")

        );
    }
}
