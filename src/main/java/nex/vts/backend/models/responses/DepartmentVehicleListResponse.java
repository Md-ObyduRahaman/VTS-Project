package nex.vts.backend.models.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class DepartmentVehicleListResponse {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("total vehicle")
    private Integer totalVehicle;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("Vehicle Details")
    private List<DeptAccVehicleList> deptAccVehicleLists;
}
