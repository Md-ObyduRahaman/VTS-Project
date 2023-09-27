package nex.vts.backend.services;

import nex.vts.backend.repoImpl.Vehicle_Location_Implementation;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@SuppressWarnings("all")
public class Vehicle_Location_Service {

    private Vehicle_Location_Implementation locationImplementation;


    public Vehicle_Location_Service(Vehicle_Location_Implementation locationImplementation) {
        this.locationImplementation = locationImplementation;
    }

    public Optional getVehicleLatitudeLongitude() {
        return locationImplementation.getReverseGeocoder();
    }

    public Optional getVehicleLocationDetails(Integer vehicleId){
        return locationImplementation.getVehicleLocation(vehicleId);
    }
}

