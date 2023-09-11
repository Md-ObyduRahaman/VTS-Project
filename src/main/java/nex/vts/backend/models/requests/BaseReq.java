package nex.vts.backend.models.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BaseReq {

    @JsonProperty("appId")
    public String appId;

    @JsonProperty("apiToken")
    public String apiToken;

    @JsonProperty("apiName")
    public String apiName;

    @JsonProperty("deviceType")
    public int deviceType;

}
