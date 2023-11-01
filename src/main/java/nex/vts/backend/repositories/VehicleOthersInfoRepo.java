package nex.vts.backend.repositories;



import nex.vts.backend.models.responses.VehicleOthersInfoModel;


import java.util.Optional;

public interface VehicleOthersInfoRepo {


    public Optional<VehicleOthersInfoModel> getVehicleOthersInfo(Integer rowID,Integer deviceType);
}
