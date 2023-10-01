package nex.vts.backend.repositories;


import java.util.Optional;

public interface Vehicle_Location_Repo {

    Optional getVehicleLocation(Integer vehicleId);

    Optional getReverseGeocoder(/*Integer xLatitude,Integer xLongitude*/);

    Optional getVehicleDistrict();
    Optional getVehicleThana(Integer thanaId);
    Optional getVehicleRoad(Integer districtId);
}
