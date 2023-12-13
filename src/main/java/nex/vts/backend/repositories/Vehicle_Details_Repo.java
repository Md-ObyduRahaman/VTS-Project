package nex.vts.backend.repositories;

import nex.vts.backend.models.responses.VehicleDetails;

import java.sql.SQLException;
import java.util.List;

public interface Vehicle_Details_Repo {
    Object getVehicleDetails(Integer userType, Integer profileId,String schemaName) throws SQLException;
//    Object getVehiclePermission(Integer userType, Integer profileId, Integer parentId, Integer vehicleId);
}
