package nex.vts.backend.repositories;

import nex.vts.backend.models.responses.VehicleStateInfoOra;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface VehicleStateRepo
{
    Optional<ArrayList<VehicleStateInfoOra>>  findVehicleStateInfoInfo(Integer parentProfileId,Integer userType,Integer userId,String SPECIFIC_VEHICLE_ID,int offSet,int limit);
    int  findTotalNumber(Integer parentProfileId,Integer userType,Integer userId,String SPECIFIC_VEHICLE_ID,int offSet);

}
