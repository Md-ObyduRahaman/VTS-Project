package nex.vts.backend.repoImpl;

import nex.vts.backend.models.User;
import nex.vts.backend.repositories.VehicleSettingRepo;
import oracle.jdbc.OracleTypes;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Map;


@Service
public class VehicleSettingImpl implements VehicleSettingRepo {

    @Autowired
    private JdbcTemplate jdbcTemplete;

    private final DataSource dataSource;
    SimpleJdbcCall getAllStatesJdbcCall;


    @Autowired
    public VehicleSettingImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    @Override
    public Integer getDiffSettingInfo(String p_permision_type, Integer p_profile_type, Integer p_profile_id, Integer p_parent_profile_id, Integer p_vehicle_id) {

        String sql = "SELECT CHECK_VEH_PROFILE_PERMISSION(?,?,?,?,?) FROM dual";

        String result = jdbcTemplete.queryForObject(
                sql,
                new Object[]{p_permision_type, p_profile_type, p_profile_id, p_parent_profile_id, p_vehicle_id},
                String.class
        );

        return Integer.valueOf(result);
    }



    @Override
    public String modify_vehicle_profile(String p_type, Integer p_profile_type, Integer p_profile_id, Integer p_parent_profile_id,
                                         Integer p_vehicle_id, Integer p_login_status, String p_pass, Integer p_max_speed,
                                         String p_sms, String p_email, boolean p_multiple_alert, Integer p_safe_mode) throws SQLException {

        String out = null;
        Connection connection = dataSource.getConnection();
        CallableStatement callableStatement = connection.prepareCall("{call modify_vehicle_profile (?, ?, ?, ?, ?, ?,?,?,?,?,?,?,?)}");

        // Set the parameters
        callableStatement.setString(1, p_type);
        callableStatement.setInt(2, p_profile_type);
        callableStatement.setInt(3, p_profile_id);
        callableStatement.setInt(4, p_parent_profile_id);
        callableStatement.setInt(5, p_vehicle_id);
        callableStatement.setInt(6, p_login_status);
        callableStatement.setString(7, p_pass);
        callableStatement.setInt(8, p_max_speed);
        callableStatement.setString(9, p_sms);
        callableStatement.setString(10, p_email);
        callableStatement.setBoolean(11, p_multiple_alert);
        callableStatement.setInt(12, p_safe_mode);
        // Register the OUT parameter
        callableStatement.registerOutParameter(13, Types.VARCHAR);


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
                    System.out.println("There is no result or an unknown result");
                    // Handle it as needed
                }
                // Retrieve the value of the 7th OUT parameter
                 out = callableStatement.getString(13);
                System.out.println("Value of out: " + out);
            }
        } catch (SQLException e) {
            // Handle any exceptions that might occur during the execution
            e.printStackTrace();
        } finally {
            // Don't forget to close the callableStatement and the connection
            callableStatement.close();
            connection.close();
        }
        return out;
    }

    @Override
    public String manage_favorite_vehicle(String p_type, Integer p_profile_type, Integer p_profile_id, Integer p_parent_profile_id,
                                          Integer p_vehicle_id, Integer p_favorite_value) throws SQLException {

        String out = null;
        Connection connection = dataSource.getConnection();
        CallableStatement callableStatement = connection.prepareCall("{call manage_favorite_vehicle (?, ?, ?, ?, ?, ?,?)}");

        // Set the parameters
        callableStatement.setString(1, p_type);
        callableStatement.setInt(2, p_profile_type);
        callableStatement.setInt(3, p_profile_id);
        callableStatement.setInt(4, p_parent_profile_id);
        callableStatement.setInt(5, p_vehicle_id);
        callableStatement.setInt(6, p_favorite_value);
        // Register the OUT parameter
        callableStatement.registerOutParameter(7, Types.VARCHAR);


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
                    System.out.println("There is no result or an unknown result");
                    // Handle it as needed
                }
                // Retrieve the value of the 7th OUT parameter
                out = callableStatement.getString(7);
                System.out.println("Value of out: " + out);
            }
        } catch (SQLException e) {
            // Handle any exceptions that might occur during the execution
            e.printStackTrace();
        } finally {
            // Don't forget to close the callableStatement and the connection
            callableStatement.close();
            connection.close();
        }
        return out;
    }


}
