package nex.vts.backend.models.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Setter;

import java.util.Optional;


@Data
public class GetDriverInfoObj {

    @JsonProperty("DriverInfoData")
    private Optional<DriverInfoModel> driverInfoModels;


}
