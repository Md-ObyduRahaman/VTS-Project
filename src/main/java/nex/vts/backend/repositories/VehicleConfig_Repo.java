package nex.vts.backend.repositories;

import java.util.Optional;

public interface VehicleConfig_Repo {

    Object getVehicleSetings(Integer vehicleId);

    void setVehicleSetings(String cellPhone,String email,String maxCarSpeed,boolean isFavourite);

}
