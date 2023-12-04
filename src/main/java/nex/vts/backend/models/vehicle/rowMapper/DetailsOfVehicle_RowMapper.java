package nex.vts.backend.models.vehicle.rowMapper;

import nex.vts.backend.models.responses.DetailsOfVehicle;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DetailsOfVehicle_RowMapper implements RowMapper<DetailsOfVehicle> {
    @Override
    public DetailsOfVehicle mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new DetailsOfVehicle(
                rs.getString("id"),
                rs.getString("vehicle_name"),
                rs.getString("engine_status"),
                rs.getFloat("speed"),
                rs.getInt("is_favorite"),
                rs.getInt("vehicle_icon_type"),
                rs.getString("user_defined_vehicle_name"),
                rs.getInt("ICON_TYPE_ON_MAP"),
                rs.getInt("ICON_TYPE_RUNNING"),
                rs.getInt("ICON_TYPE_STOPPED"),
                rs.getInt("ICON_TYPE_STATIONARY"),
                rs.getString("registration_number"),
                rs.getString("color"),
                rs.getString("vendor"),
                rs.getString("model"),
                rs.getString("max_speed"),
                rs.getInt("iscolor")
        );
    }
}
