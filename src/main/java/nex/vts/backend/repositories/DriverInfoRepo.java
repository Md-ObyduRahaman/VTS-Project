package nex.vts.backend.repositories;

import nex.vts.backend.models.responses.VehDriverInfo;

import java.util.ArrayList;
import java.util.Optional;

public interface DriverInfoRepo
{
//    Optional<DriverInfoModel> findDriverInfo(Integer id);
////    public Optional<DriverInfoModel> findAllDriverinfo(Integer Id);
//    Optional<VehProfileChangePermision> findVehProfileChangePermision(Integer vehicle_id, Integer profile_type);

    Optional<ArrayList<VehDriverInfo>> get_DriverInfo(String userId, int vehRowId, int deviceType, int userType);  // [New Dev: 2024-01-22]

}
