package nex.vts.backend.models.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class LoginResponse {

    @JsonProperty("loginSuccess")
    public boolean loginSuccess;

    @JsonProperty("serverDateTime")
    public String serverDateTime;

    @JsonProperty("token")
    public String token;

    public int profileType;

    public int profileId;

    public int mainAccountId;

    public int parentId;

    public String userName;

    //IND_LOGIN,ICON_TYPE,COMPANY_ID


}
