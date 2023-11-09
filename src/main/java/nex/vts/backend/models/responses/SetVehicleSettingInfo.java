package nex.vts.backend.models.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import nex.vts.backend.models.requests.BaseReq;
import nex.vts.backend.models.requests.LoginReq;

@Data
public class SetVehicleSettingInfo /*extends LoginReq*/ extends BaseReq {

    @JsonProperty("permissionData")
    private  VehicleOthersInfoModel vehicleOthersInfoModel;

    private Integer vehicleId;

    public int parentId;

    @JsonProperty("password")
    public String password;

    @JsonProperty("username")
    public String username;

    public int profileType;

    public int profileId;


}
