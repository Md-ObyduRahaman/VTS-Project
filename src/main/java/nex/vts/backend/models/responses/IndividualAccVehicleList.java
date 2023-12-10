package nex.vts.backend.models.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class IndividualAccVehicleList {
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
    private String maxSpeed;
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
    @JsonProperty("iconTypeRunnig")
    private int iconTypeRunnig;

    @JsonProperty("iscolor")
    private int iscolor;

    public IndividualAccVehicleList(String id, String vehicleName, String engineStatus, float speed, int isFavourite, int vehicleIconType, String userDefinedVehicleName, int iconTypeOnMap, int iconTypeRunnig, int iconTypeStopped, int iconTypeStationary, String registrationNumber, String color, String vendor, String model, String maxSpeed, int iscolor) {

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
        this.iconTypeRunnig = iconTypeRunnig;
        this.iscolor = iscolor;
    }
}