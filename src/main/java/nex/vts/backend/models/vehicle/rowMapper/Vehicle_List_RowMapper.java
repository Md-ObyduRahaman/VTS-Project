package nex.vts.backend.models.vehicle.rowMapper;

import nex.vts.backend.models.vehicle.Vehicle_List;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Vehicle_List_RowMapper implements RowMapper<Vehicle_List>  {

    @Override
    public Vehicle_List mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Vehicle_List(
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
                rs.getInt("max_speed")
        );
    }
}
