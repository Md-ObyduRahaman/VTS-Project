package nex.vts.backend.repoImpl;

import nex.vts.backend.models.responses.ForgotPassWordPayLoadData;
import nex.vts.backend.models.responses.ForgotPassWordResponseData;
import nex.vts.backend.repositories.ForgotPasswordRepo;
import nex.vts.backend.repositories.ResetPasswordRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;

@Service
public class ResetPasswordImpl implements ResetPasswordRepo {

    @Autowired
    private JdbcTemplate jdbcTemplete;

    private final DataSource dataSource;



    @Autowired
    public ResetPasswordImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public ForgotPassWordResponseData getResetPassword(ForgotPassWordPayLoadData payLoadData,String p_operation_type) throws SQLException {

        ForgotPassWordResponseData forgotPassWordResponseData=new ForgotPassWordResponseData();
        Connection  connection = dataSource.getConnection();
        CallableStatement  callableStatement = connection.prepareCall("{call GPSNEXGP.manage_change_password (?, ?, ?, ?, ?, ?,?)}");

        // Set the parameters
        callableStatement.setString(1, p_operation_type);
        callableStatement.setString(2, payLoadData.getUserName());
        callableStatement.setString(3, payLoadData.getOtp());
        callableStatement.setString(4, null);
        callableStatement.registerOutParameter(5, Types.INTEGER);
        callableStatement.registerOutParameter(6, Types.VARCHAR);
        callableStatement.setInt(7, payLoadData.getAppType());

        try {
            // Execute the stored procedure
            boolean result = callableStatement.execute();

            // Check if the result is a ResultSet (if applicable)
            if (result) {
                ResultSet resultSet = callableStatement.getResultSet();
                // Process the result set here
            } else {
                // Handle the case where the result is an update count or there's no result
                int updateCount = callableStatement.getUpdateCount();
                if (updateCount != -1) {
                    // This is an update count (e.g., number of rows affected)
                    // Handle it accordingly
                } else {
                    // There is no result or an unknown result
                    // System.out.println("There is no result or an unknown result");
                    // Handle it as needed
                }
                // Retrieve the value of the 7th OUT parameter
                Integer p_operation_stat = callableStatement.getInt(5);
                String p_response = callableStatement.getString(6);
                forgotPassWordResponseData.setMessage(p_response);
                forgotPassWordResponseData.setP_operation_stat(p_operation_stat);
            }
        } catch (SQLException e) {
            // Handle any exceptions that might occur during the execution
            e.printStackTrace();
        } finally {
            // Don't forget to close the callableStatement and the connection
            callableStatement.close();
            connection.close();
        }
        return forgotPassWordResponseData;
    }
}
