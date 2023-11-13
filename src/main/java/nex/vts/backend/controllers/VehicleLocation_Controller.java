package nex.vts.backend.controllers;

import nex.vts.backend.services.Vehicle_Location_Service;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@RequestMapping(value = "/api/private")
public class VehicleLocation_Controller {

    private final Vehicle_Location_Service locationService;

    public VehicleLocation_Controller(Vehicle_Location_Service locationService) {
        this.locationService = locationService;
    }

    @GetMapping("/v1/vehicle/location")
    public ResponseEntity<?> getVehicleLocation(@RequestHeader(value = "data") Integer vehicleId) throws SQLException {
        return ResponseEntity.ok(locationService.getVehicleLocationDetails(vehicleId));
    }

}
