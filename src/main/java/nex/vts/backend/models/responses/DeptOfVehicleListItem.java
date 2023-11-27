package nex.vts.backend.models.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class DeptOfVehicleListItem {
    @JsonProperty("iconTypeStationary")
    private int iconTypeStationary;
    @JsonProperty("iconTypeOnMap")
    private int iconTypeOnMap;
    @JsonProperty("vehicleName")
    private String vehicleName;
    @JsonProperty("iconTypeStopped")
    private int iconTypeStopped;
    @JsonProperty("color")
    private String color;
    @JsonProperty("userDefinedVehicleName")
    private String userDefinedVehicleName;
    @JsonProperty("maxSpeed")
    private int maxSpeed;
    @JsonProperty("speed")
    private float speed;
    @JsonProperty("engineStatus")
    private String engineStatus;
    @JsonProperty("registrationNumber")
    private String registrationNumber;
    @JsonProperty("vendor")
    private String vendor;
    @JsonProperty("vehicleIconType")
    private int vehicleIconType;
    @JsonProperty("model")
    private String model;
    @JsonProperty("id")
    private String id;
    @JsonProperty("isFavourite")
    private int isFavourite;
    @JsonProperty("iconTypeRunning")
    private int iconTypeRunning;

    public DeptOfVehicleListItem(int iconTypeStationary, int iconTypeOnMap, String vehicleName, int iconTypeStopped, String color, String userDefinedVehicleName, int maxSpeed, float speed, String engineStatus, String registrationNumber, String vendor, int vehicleIconType, String model, String id, int isFavourite, int iconTypeRunnig) {
        this.iconTypeStationary = iconTypeStationary;
        this.iconTypeOnMap = iconTypeOnMap;
        this.vehicleName = vehicleName;
        this.iconTypeStopped = iconTypeStopped;
        this.color = color;
        this.userDefinedVehicleName = userDefinedVehicleName;
        this.maxSpeed = maxSpeed;
        this.speed = speed;
        this.engineStatus = engineStatus;
        this.registrationNumber = registrationNumber;
        this.vendor = vendor;
        this.vehicleIconType = vehicleIconType;
        this.model = model;
        this.id = id;
        this.isFavourite = isFavourite;
        this.iconTypeRunning = iconTypeRunnig;
    }
}