package nex.vts.backend.repositories;

import java.sql.SQLException;

public interface VehicleDetails_Repo {
    Object getVehicleDetailForGpAndM2M(Integer userType, Integer profileId, String schemaName);
//    Object getVehiclePermission(Integer userType, Integer profileId, Integer parentId, Integer vehicleId);
}
