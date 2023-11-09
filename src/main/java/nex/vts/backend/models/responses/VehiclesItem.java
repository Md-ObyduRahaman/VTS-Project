package nex.vts.backend.models.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VehiclesItem {

    @JsonProperty("vehicleIconType")
    public int vehicleIconType;

    @JsonProperty("isFavorite")
    public int isFavorite;

    @JsonProperty("iconTypeStopped")
    public int iconTypeStopped;

    @JsonProperty("id")
    public int id;

    @JsonProperty("vehicleName")
    public String vehicleName;

    @JsonProperty("iconTypeStationary")
    public int iconTypeStationary;

    @JsonProperty("maxSpeed")
    public int maxSpeed;

    @JsonProperty("speed")
    public double speed;

    @JsonProperty("iconTypeRunning")
    public int iconTypeRunning;

    @JsonProperty("engineStatus")
    public String engineStatus;

    @JsonProperty("userDefinedVehicleName")
    public String userDefinedVehicleName;

    @JsonProperty("iconTypeOnMap")
    public int iconTypeOnMap;

}