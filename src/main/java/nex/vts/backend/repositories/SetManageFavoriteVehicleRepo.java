package nex.vts.backend.repositories;

import nex.vts.backend.models.responses.ManageFavoriteVehicle;

public interface SetManageFavoriteVehicleRepo {

    public String setManageFavoriteVehicle(ManageFavoriteVehicle manageFavoriteVehicle,Integer deviceType);
}
