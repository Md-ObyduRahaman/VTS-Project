package nex.vts.backend.models.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VehicleDetailsResponse {

    @JsonProperty("response")
    public VehicleDetailsResponse vehicleDetailsResponse;

    @JsonProperty("code")
    public int code;

    @JsonProperty("vehicleInfo")
    public VehicleDetails vehicleDetails;

    public VehicleDetailsResponse getResponse() {
        return vehicleDetailsResponse;
    }

    public int getCode() {
        return code;
    }

    public VehicleDetails getVehicleInfo() {
        return vehicleDetails;
    }
}