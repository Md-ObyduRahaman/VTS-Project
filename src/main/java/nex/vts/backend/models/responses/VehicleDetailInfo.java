package nex.vts.backend.models.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class VehicleDetailInfo {

    @JsonProperty("id")
    private int id;

    @JsonProperty("iconType")
    private int iconType;

    @JsonProperty("iconTypeOnMap")
    private int iconTypeOnMap;

    @JsonProperty("iconTypeRunning")
    private int iconTypeRunning;

    @JsonProperty("iconTypeStopped")
    private int iconTypeStopped;

    @JsonProperty("iconTypeStationary")
    private int iconTypeStationary;

    @JsonProperty("iconTypeOverSpeed")
    private int iconTypeOverSpeed;

    @JsonProperty("userId")
    private String userId;

    @JsonProperty("fullName")
    private String fullName;

    @JsonProperty("cellPhone")
    private String cellPhone;

    @JsonProperty("carColor")
    private String carColor;

    @JsonProperty("carVendor")
    private String carVendor;

    @JsonProperty("carModel")
    private String carModel;

    @JsonProperty("customUserId")
    private String customUserId;

    @JsonProperty("email")
    private String email;

    @JsonProperty("vehicleImage")
    private int vehicleImage;

    @JsonProperty("driverId")
    private String driverId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("fName")
    private String fName;

    @JsonProperty("licence")
    private String licence;

    @JsonProperty("address")
    private String address;

    @JsonProperty("cell")
    private String cell;

    @JsonProperty("maxCarSpeed")
    private String maxCarSpeed;

    @JsonProperty("driverPhoto")
    private int driverPhoto;

    @JsonProperty("carImage")
    private String carImage;

    @JsonProperty("carOption")
    private String carOption;
}