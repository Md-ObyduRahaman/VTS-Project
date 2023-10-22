package nex.vts.backend.models.responses;

import lombok.Data;

@Data
public class ForgotPassWordResponseData {

    private Integer p_operation_stat,token,code;
    private String message;
}
