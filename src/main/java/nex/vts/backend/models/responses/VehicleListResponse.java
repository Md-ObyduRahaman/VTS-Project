package nex.vts.backend.models.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class VehicleListResponse {

    @JsonProperty("total vehicle")
    private int totalVehicle;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("Details Of Vehicle")
    private List<DetailsOfVehicleItem> detailsOfVehicle;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("Detail Of Vehicle")
    private List<DeptOfVehicleListItem> deptOfVehicleList;
}