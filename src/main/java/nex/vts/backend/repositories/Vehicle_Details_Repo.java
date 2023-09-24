package nex.vts.backend.repositories;

import nex.vts.backend.models.vehicle.Vehicle_Details;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface Vehicle_Details_Repo {

    Object getVehicleDetails(Integer userType, Integer profileId, Integer vehicleId) throws SQLException;
    Object getVehiclePermission(Integer userType,Integer profileId,Integer parentId,Integer vehicleId);
}
