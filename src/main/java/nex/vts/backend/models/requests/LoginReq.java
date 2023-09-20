package nex.vts.backend.models.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginReq extends BaseReq {

    @JsonProperty("password")
    public String password;

    @JsonProperty("username")
    public String username;
}
