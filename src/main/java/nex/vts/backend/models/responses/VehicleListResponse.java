//package nex.vts.backend.models.responses;
//
//import com.fasterxml.jackson.annotation.JsonInclude;
//import com.fasterxml.jackson.annotation.JsonProperty;
//import lombok.Getter;
//import lombok.Setter;
//
//import java.util.List;
//
//@Getter
//@Setter
//public class VehicleListResponse {
//
//    @JsonInclude(JsonInclude.Include.NON_NULL)
//    @JsonProperty("total vehicle")
//    private Integer totalVehicle;
//
//    @JsonInclude(JsonInclude.Include.NON_NULL)
//    @JsonProperty("Mother Account Vehicle Details")
//    private List<MotherAccVehicleList> motherAccVehicleLists;
//
//    @JsonInclude(JsonInclude.Include.NON_NULL)
//    @JsonProperty("Dept Account Vehicle Details")
//    private List<DeptAccVehicleList> deptAccVehicleLists;
//
//    @JsonInclude(JsonInclude.Include.NON_NULL)
//    @JsonProperty("Individual Account Vehicle Details ")
//    private IndividualAccVehicleList individualAccVehicleListList;
//
//
//}