package nex.vts.backend.models.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IndividualVehicleListResponse {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("Vehicle Details")
    private IndividualAccVehicleList individualAccVehicleListList;
}
