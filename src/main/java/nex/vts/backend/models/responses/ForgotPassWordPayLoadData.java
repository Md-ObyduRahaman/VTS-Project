package nex.vts.backend.models.responses;


import lombok.Data;
import nex.vts.backend.models.requests.LoginReq;

@Data
public class ForgotPassWordPayLoadData extends LoginReq {
    private Integer appType;
    private  String otp,reTypePassword,newPassword;

}
