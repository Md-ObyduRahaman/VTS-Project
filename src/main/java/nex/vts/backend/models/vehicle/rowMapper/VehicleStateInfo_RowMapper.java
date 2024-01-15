package nex.vts.backend.models.vehicle.rowMapper;


import nex.vts.backend.models.responses.VehicleStateInfoOra;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class VehicleStateInfo_RowMapper implements RowMapper<VehicleStateInfoOra> {
    @Override
    public VehicleStateInfoOra mapRow(ResultSet rs, int rowNum) throws SQLException {
        VehicleStateInfoOra vehicleStateInfo = new VehicleStateInfoOra();
        vehicleStateInfo.setVehId(rs.getInt("VEHICLE_ID"));
        vehicleStateInfo.setEnginStat(rs.getString("ENGIN"));
        vehicleStateInfo.setVehStat(getVehicleMaintenanceStatus(rs.getInt("VEH_MAINTENANCE")));
        vehicleStateInfo.setDateTime(rs.getString("VDATE"));
        vehicleStateInfo.setLocationDetails(rs.getString("LAT") + "," + rs.getString("LON"));
        // Map other columns as needed

        return vehicleStateInfo;
    }
    public static String getVehicleMaintenanceStatus(int xID) {
        String result;

        if (xID == 1) {
            result = "On Maintenance";
        } else if (xID == 2) {
            result = "Release Maintenance";
        } else {
            result = "Active";
        }

        return result;
    }
}
