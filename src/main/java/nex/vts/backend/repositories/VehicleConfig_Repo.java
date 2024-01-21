package nex.vts.backend.repositories;

import java.util.Optional;

public interface VehicleConfig_Repo {

    Object getVehicleSettings(Integer vehicleId);

    int setVehicleSettings(String cellPhone,String email,String maxCarSpeed,int isFavourite,Integer vehicleId);

}
