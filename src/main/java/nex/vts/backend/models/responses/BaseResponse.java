package nex.vts.backend.models.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"status", "data", "errorCode", "errorMsg"})
public class BaseResponse {

    @JsonProperty("data")
    public Object data;

    @JsonProperty("errorCode")
    public int errorCode;

    @JsonProperty("status")
    public boolean status;

    @JsonProperty("errorMsg")
    public String errorMsg = "";

}
