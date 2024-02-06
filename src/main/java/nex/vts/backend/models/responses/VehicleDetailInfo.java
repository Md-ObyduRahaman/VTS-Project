package nex.vts.backend.models.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.websocket.Decoder;
import lombok.*;

import java.io.InputStream;
import java.sql.Blob;

@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class VehicleDetailInfo {

    @JsonProperty("vehId")
    private Integer vehicleId;

    @JsonProperty("carRegistrationNo")
    private String carNo;

    @JsonProperty("vehName")
    private String userId; /*todo not used*/

    @JsonProperty("fullName")
    private String fullName;

    @JsonProperty("contactCell")
    private String cellPhone;

    @JsonProperty("contactEmail")
    private String email;

    @JsonProperty("vehColor")
    private String carColor;

    @JsonProperty("carVendor")
    private String carVendor;

    @JsonProperty("carModel")
    private String carModel;

/*    @JsonProperty("customUserId")
    private String customUserId; *//*todo not used*/

    @JsonProperty("vehIconType")
    private Integer iconType;

    @JsonProperty("vehIconTypeOnMap")
    private Integer iconTypeOnMap;

    @JsonProperty("vehIconTypeRunning")
    private Integer iconTypeRunning;

    @JsonProperty("vehIconTypeStopped")
    private Integer iconTypeStopped;

    @JsonProperty("vehIconTypeStationary")
    private Integer iconTypeStationary;

    @JsonProperty("vehIconTypeOverSpeed")
    private Integer iconTypeOverSpeed;

/*
    @JsonProperty("vehImage")
    private String vehicleImage;
*/

    @JsonProperty("driverRowId")
    private Integer driverId;

    @JsonProperty("driverName")
    private String dName;

    @JsonProperty("driverFather")
    private String fName;

    @JsonProperty("driverLicense")
    private String dLicense;

    @JsonProperty("driverAddress")
    private String dAddress;

    @JsonProperty("driverCell")
    private String dCell;

    @JsonProperty("maxCarSpeed")
    private Integer maxCarSpeed;

    @JsonProperty("driverPhoto")
    private String driverPhoto;

    @JsonProperty("carImage")
    private String carImage;

}