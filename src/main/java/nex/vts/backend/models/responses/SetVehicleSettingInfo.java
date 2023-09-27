package nex.vts.backend.models.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import nex.vts.backend.models.requests.LoginReq;

@Data
public class SetVehicleSettingInfo extends LoginReq {

    @JsonProperty("permissionData")
    private  VehicleOthersInfoModel vehicleOthersInfoModel;

    private Integer vehicleId;


}
