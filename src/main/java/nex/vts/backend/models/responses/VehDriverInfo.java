package nex.vts.backend.models.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonPropertyOrder({"driverRowId","driverName","driverFather"})
public class VehDriverInfo {
    private String driverRowId;
    private String driverName, driverFather, driverAddress, driverCell;
    private String driverDOB ,driverNID, driverLicense;

    //    private String DRIVER_PHOTO, D_IMAGE;
//    private String MAX_CAR_SPEED;

}
