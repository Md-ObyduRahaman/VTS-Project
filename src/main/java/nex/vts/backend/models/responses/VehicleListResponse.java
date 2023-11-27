package nex.vts.backend.models.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class VehicleListResponse {
    @JsonProperty("total vehicle")
    private int totalVehicle;
    @JsonProperty("details of Vehicle")
    private List<DetailsOfVehicleItem> detailsOfVehicle;
    @JsonProperty("Dept Of Vehicle List")
    private List<DeptOfVehicleListItem> deptOfVehicleList;
}