package nex.vts.backend.models.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Setter;

import java.util.Optional;


@Setter
public class GetDriverInfoObj {

    @JsonProperty("DriverInfoData")
    private Optional<DriverInfoModel> driverInfoModels;
}
