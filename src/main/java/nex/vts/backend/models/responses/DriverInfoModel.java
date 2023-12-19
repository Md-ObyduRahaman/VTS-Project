package nex.vts.backend.models.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.sql.Blob;


@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DriverInfoModel {


    private String  USERID;
            private Integer ID;
            @JsonProperty("name")
    private String D_NAME;
    @JsonProperty("fname")
    private String D_FNAME;
    @JsonProperty("license")
    private String D_LICENSE;
    @JsonProperty("address")
    private String D_ADDRESS;
    @JsonProperty("cell")
    private String D_CELL;
    @JsonProperty("dob")
    private String D_DOB;
    @JsonProperty("driver_photo")
    private Blob DRIVER_PHOTO;
    @JsonProperty("vehProfileChangePermision")
    private VehProfileChangePermision vehProfileChangePermision;

}
