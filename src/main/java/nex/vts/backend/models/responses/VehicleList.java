package nex.vts.backend.models.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class VehicleList {


    @JsonProperty("vehiclelistItem")
    private List<VehiclelistItem> vehiclelistItem;

    public void setVehiclelist(List<VehiclelistItem> vehiclelistItem) {
        this.vehiclelistItem = vehiclelistItem;
    }


}
