package nex.vts.backend.models.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Blob;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class VehicleDetailInfo {

    @JsonProperty("vehicleId")
    private Integer vehicleId;

    private String userId;

    @JsonProperty("fullName")
    private String fullName;

    @JsonProperty("cellPhone")
    private String cellPhone;

    private String carColor;

    private String carVendor;

    private String carModel;

    private String customUserId;

    private String email;

    private Integer iconType;

    private Integer iconTypeOnMap;

    private Integer iconTypeRunning;

    private Integer iconTypeStopped;

    private Integer iconTypeStationary;

    private Integer iconTypeOverSpeed;

    private Blob vehicleImage;

    private Integer driverId;

    private String dName;

    private String fName;

    private String dLicense;

    private String dAddress;

    private String dCell;

    private Integer maxCarSpeed;

    private String driverPhoto;

    private String carImage;

}