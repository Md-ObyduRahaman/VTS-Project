package nex.vts.backend.repositories;

import nex.vts.backend.models.vehicle.Vehicle_Details;

import java.util.List;
import java.util.Optional;

public interface Vehicle_Details_Repo {

    List<Vehicle_Details> getVehicleDetails(Integer userType, Integer profileId, Integer vehicleId);
    Object getVehiclePermission(Integer userType,Integer profileId,Integer parentId,Integer vehicleId);
}
