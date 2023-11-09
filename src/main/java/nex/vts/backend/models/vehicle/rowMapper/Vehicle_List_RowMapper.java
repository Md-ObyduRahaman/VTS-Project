package nex.vts.backend.models.vehicle.rowMapper;
import nex.vts.backend.models.responses.VehiclesItem;
import nex.vts.backend.models.vehicle.Vehicle_List;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Vehicle_List_RowMapper implements RowMapper<VehiclesItem>  {

    @Override
    public VehiclesItem mapRow(ResultSet rs, int rowNum) throws SQLException {
        VehiclesItem vehicle = new VehiclesItem();
        vehicle.id = Integer.parseInt(rs.getString("id"));
        vehicle.vehicleName = rs.getString("vehicle_name");
        vehicle.engineStatus = rs.getString("engine_status");
        vehicle.speed = rs.getFloat("speed");
        vehicle.isFavorite = rs.getInt("is_favorite");
        vehicle.vehicleIconType = rs.getInt("vehicle_icon_type");
        vehicle.userDefinedVehicleName = rs.getString("user_defined_vehicle_name");
        vehicle.iconTypeOnMap = rs.getInt("ICON_TYPE_ON_MAP");
        vehicle.iconTypeRunning = rs.getInt("ICON_TYPE_RUNNING");
        vehicle.iconTypeStopped = rs.getInt("ICON_TYPE_STOPPED");
        vehicle.iconTypeStationary = rs.getInt("ICON_TYPE_STATIONARY");
        vehicle.maxSpeed = rs.getInt("max_speed");
/*        return new Vehicle_List(

*//*                rs.getString("id"),
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
                rs.getInt("max_speed")*//*

        );*/
        return vehicle;
    }
}
