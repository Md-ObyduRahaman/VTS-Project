package nex.vts.backend.Model.vehicleList;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import nex.vts.backend.Model.Response.BaseResponse;

public class VehicleList  {



    @JsonProperty("vehiclelistItem")
    private List<VehiclelistItem> vehiclelistItem;

    public void setVehiclelist(List<VehiclelistItem> vehiclelistItem) {
        this.vehiclelistItem = vehiclelistItem;
    }


}