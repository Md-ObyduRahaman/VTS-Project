package nex.vts.backend.repositories;

import nex.vts.backend.models.responses.DriverInfoModel;
import nex.vts.backend.models.responses.VehProfileChangePermision;

import nex.vts.backend.models.responses.VehicleStateInfoOra;

import java.util.ArrayList;
import java.util.Optional;

public interface VehicleStateRepo
{
    Optional<ArrayList<VehicleStateInfoOra>>  findVehicleStateInfoInfo(Integer parentProfileId);

}
