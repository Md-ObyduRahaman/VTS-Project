package nex.vts.backend.models.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Setter;


@Setter
public class GetDriverInfoObj {

    @JsonProperty("DriverInfoData")
    private DriverInfoModel driverInfoModels;
}
