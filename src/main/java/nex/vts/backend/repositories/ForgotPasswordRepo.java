package nex.vts.backend.repositories;

import nex.vts.backend.models.responses.ForgotPassWordPayLoadData;
import nex.vts.backend.models.responses.ForgotPassWordResponseData;

import java.sql.SQLException;

public interface ForgotPasswordRepo {

    public ForgotPassWordResponseData getForgotPassword(ForgotPassWordPayLoadData payLoadData) throws SQLException;
}
