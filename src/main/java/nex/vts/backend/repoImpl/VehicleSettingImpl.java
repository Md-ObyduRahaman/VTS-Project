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
import java.util.ArrayList;
import java.util.Map;


@Service
public class VehicleSettingImpl implements VehicleSettingRepo {

    @Autowired
    private JdbcTemplate jdbcTemplete;
    SimpleJdbcCall getAllStatesJdbcCall;

    public VehicleSettingImpl(DataSource dataSource) {
        this.jdbcTemplete = new JdbcTemplate(dataSource);
        this.getAllStatesJdbcCall = new SimpleJdbcCall(dataSource);
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
    public String getModifyVicleProfileResponse(String p_type, Integer p_profile_type, Integer p_profile_id, Integer p_parent_profile_id, Integer p_vehicle_id, Integer p_login_status, String p_pass, String p_max_speed, String p_sms, String p_email, Integer p_multiple_alert, Integer p_safe_mode) {


        String out = null;

        SimpleJdbcCall jdbcCall = getAllStatesJdbcCall.withProcedureName("modify_vehicle_profile");

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("p_type", p_type);
        params.addValue("p_profile_type", p_profile_type);
        params.addValue("p_profile_id", p_profile_id);
        params.addValue("p_parent_profile_id", p_parent_profile_id);
        params.addValue("p_login_status", p_login_status);
        params.addValue("p_vehicle_id", p_vehicle_id);
        params.addValue("p_pass", p_pass);
        params.addValue("p_max_speed", p_max_speed);
        params.addValue("p_sms", p_sms);
        params.addValue("p_email", p_email);
        params.addValue("p_multiple_alert", p_multiple_alert);
        params.addValue("p_safe_mode", p_safe_mode);

        try {
            Map<String, Object> output = jdbcCall.execute(params);
            JSONObject json = new JSONObject(output);
             out = json.get("P_RESPONSE").toString();

        } catch (NumberFormatException e) {

            System.err.println("You are trying to pass wrong type of arguments ,please check arguments index position with procedure arguments index position, and try to pass write f DataType. Please check Error massage.\nError massage: " + e.getMessage());

        } catch (BadSqlGrammarException e) {
            e.printStackTrace();
            System.err.println("Please check your procedure Name and number of arguments. Please check Error massage.\nError massage: " + e.getMessage());

        } catch (JSONException e) {
            System.err.println("Please check your output cursor name, must be similar with oracle procedure out cursor. Please check Error massage.\nError massage: " + e.getMessage());

        } catch (NullPointerException e) {
            System.err.println(" Please check your SimpleJdbcCall object, might be dataSource is not assigned.  Please check Error massage.\nError massage: " + e.getMessage());
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("Please check your number of parameter that you are trying to pass as arguments, pass right number of parameter.  Please check Error massage.\nError massage: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Exception Occurred: " + e.getMessage());
        }
        return out;
    }


}
