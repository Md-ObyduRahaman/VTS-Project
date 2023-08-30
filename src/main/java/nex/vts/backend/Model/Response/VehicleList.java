package nex.vts.backend.Model.Response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VehicleList  {



    @JsonProperty("vehiclelistItem")
    private List<VehiclelistItem> vehiclelistItem;

    public void setVehiclelist(List<VehiclelistItem> vehiclelistItem) {
        this.vehiclelistItem = vehiclelistItem;
    }


}