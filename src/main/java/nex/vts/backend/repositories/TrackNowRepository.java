package nex.vts.backend.repositories;

import nex.vts.backend.models.responses.VehicleCurrentLocation;

import java.util.Optional;

public interface TrackNowRepository {
    Optional<VehicleCurrentLocation> getCurrentLocation(Integer vehicleId);
}
