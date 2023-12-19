package nex.vts.backend.repositories;

import nex.vts.backend.models.responses.DriverInfoModel;
import nex.vts.backend.models.responses.GetExpansesModel;
import nex.vts.backend.models.responses.VehProfileChangePermision;

import java.util.ArrayList;
import java.util.Optional;

public interface DriverInfoRepo
{
    Optional<DriverInfoModel> findDriverInfo(Integer id);
//    public Optional<DriverInfoModel> findAllDriverinfo(Integer Id);
    Optional<VehProfileChangePermision> findVehProfileChangePermision(Integer vehicle_id, Integer profile_type);

}
