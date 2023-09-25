package nex.vts.backend.models.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import nex.vts.backend.models.responses.LoginResponse;

public class BaseReq extends LoginResponse {

    @JsonProperty("appId")
    public String appId;

    @JsonProperty("apiToken")
    public String apiToken;

    @JsonProperty("apiName")
    public String apiName;

    @JsonProperty("deviceType")
    public int deviceType;

}
