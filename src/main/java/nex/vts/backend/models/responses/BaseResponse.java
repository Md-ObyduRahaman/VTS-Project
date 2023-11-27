package nex.vts.backend.models.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@JsonPropertyOrder({"status","apiName","data", "errorCode", "errorMsg"})
public class BaseResponse {



   @JsonProperty("apiName")
    public String apiName;

    @JsonProperty("data")
    public Object data;

    @JsonProperty("errorCode")
    public int errorCode;

    @JsonProperty("status")
    public boolean status;

    @JsonProperty("errorMsg")
    public String errorMsg = "";

}
