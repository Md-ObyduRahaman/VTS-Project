package nex.vts.backend.repositories;

import nex.vts.backend.models.responses.ForgotPassWordPayLoadData;
import nex.vts.backend.models.responses.ForgotPassWordResponseData;

import java.sql.SQLException;

public interface ResetPasswordRepo {

    public ForgotPassWordResponseData getResetPassword(ForgotPassWordPayLoadData payLoadData,String p_operation_type) throws SQLException;


}
