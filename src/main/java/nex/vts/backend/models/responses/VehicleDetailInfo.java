package nex.vts.backend.models.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.sql.Blob;

@Data
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class VehicleDetailInfo {

    @JsonProperty("vehicleId")
    private Integer vehicleId;

    @JsonProperty("registrationNumber")
    private String carNo;

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

    @JsonProperty("iconType")
    private Integer iconType;

    @JsonProperty("iconTypeOnMap")
    private Integer iconTypeOnMap;

    @JsonProperty("iconTypeRunning")
    private Integer iconTypeRunning;

    @JsonProperty("iconTypeStopped")
    private Integer iconTypeStopped;

    @JsonProperty("iconTypeStationary")
    private Integer iconTypeStationary;

    @JsonProperty("iconTypeOverSpeed")
    private Integer iconTypeOverSpeed;

    @JsonProperty("vehicleImage")
    private Blob vehicleImage;

    @JsonProperty("driverId")
    private Integer driverId;

    @JsonProperty("dName")
    private String dName;

    @JsonProperty("fName")
    private String fName;

    @JsonProperty("dLicense")
    private String dLicense;

    @JsonProperty("dAddress")
    private String dAddress;

    @JsonProperty("dCell")
    private String dCell;

    @JsonProperty("maxCarSpeed")
    private Integer maxCarSpeed;

    @JsonProperty("driverPhoto")
    private String driverPhoto;

    @JsonProperty("carImage")
    private String carImage;

}