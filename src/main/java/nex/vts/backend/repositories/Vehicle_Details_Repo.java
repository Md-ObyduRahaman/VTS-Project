package nex.vts.backend.repositories;

import java.sql.SQLException;

public interface Vehicle_Details_Repo {
    Object getVehicleDetails(Integer userType, Integer profileId, Integer vehicleId) throws SQLException;
    Object getVehiclePermission(Integer userType, Integer profileId, Integer parentId, Integer vehicleId);
}
