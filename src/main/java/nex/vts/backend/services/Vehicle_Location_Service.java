package nex.vts.backend.services;

import nex.vts.backend.repoImpl.Vehicle_Location_Repo_Imp;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class Vehicle_Location_Service {
    private Vehicle_Location_Repo_Imp locationImplementation;

    public Vehicle_Location_Service(Vehicle_Location_Repo_Imp locationImplementation) {
        this.locationImplementation = locationImplementation;
    }

    public Optional getVehicleLatitudeLongitude() {
        return locationImplementation.getReverseGeocoder();
    }

    public Optional getVehicleLocationDetails(Integer vehicleId) {
        return locationImplementation.getVehicleLocation(vehicleId);
    }

    public Optional getVehicleDistrict(){
        Optional vehicleDistrict = locationImplementation.getVehicleDistrict();
        return vehicleDistrict;
    }

    public Optional getVehicleThana(Integer thanaId){
        Optional vehicleThana = locationImplementation.getVehicleThana(thanaId);
        return vehicleThana;
    }

    public Optional getVehicleRoad(Integer districtId){
        Optional vehicleRoad = locationImplementation.getVehicleRoad(districtId);
        return vehicleRoad;
    }


}
