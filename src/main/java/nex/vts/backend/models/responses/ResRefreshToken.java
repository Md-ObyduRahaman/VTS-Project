package nex.vts.backend.models.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResRefreshToken {

    @JsonProperty("serverDateTime")
    public String serverDateTime;

    @JsonProperty("token")
    public String token;
}
