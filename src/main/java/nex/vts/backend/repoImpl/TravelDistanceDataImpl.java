package nex.vts.backend.repoImpl;

import nex.vts.backend.exceptions.AppCommonException;
import nex.vts.backend.models.responses.SpeedDataResponse;
import nex.vts.backend.models.responses.TravelDistanceDataModel;
import nex.vts.backend.repositories.SpeedDataRepo;
import nex.vts.backend.repositories.TravelDistanceDataRepo;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.TransientDataAccessException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

@Service
public class TravelDistanceDataImpl implements TravelDistanceDataRepo {

    private final Logger logger = LoggerFactory.getLogger(TravelDistanceDataImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplete;
    SimpleJdbcCall getAllStatesJdbcCall;

    public TravelDistanceDataImpl(DataSource dataSource) {
        this.jdbcTemplete = new JdbcTemplate(dataSource);
        this.getAllStatesJdbcCall = new SimpleJdbcCall(dataSource);
    }

    @Override
    public Optional<ArrayList<TravelDistanceDataModel>> getTravelDistanceData(TravelDistanceDataModel t) {


        String out = null;

        SimpleJdbcCall jdbcCall = getAllStatesJdbcCall.withProcedureName("generate_distance_report_data");

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("p_alert_type", t.getP_alert_type());
        params.addValue("p_report_type", t.getP_report_type());
        params.addValue("p_profile_type", t.getProfileType());
        params.addValue("p_profile_id", t.getProfileId());
        params.addValue("p_profile_p_id", t.getParentId());
        params.addValue("p_all_vehicle_flag", t.getP_all_vehicle_flag());
        params.addValue("p_vehicle_id", t.getVehicleId());
        params.addValue("p_date_from", t.getP_date_from());
        params.addValue("p_date_to", t.getP_date_to());

        try {
            Map<String, Object> output = jdbcCall.execute(params);
            JSONObject json = new JSONObject(output);
         //   out = json.get("P_RESPONSE").toString();

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
        return null;
    }
    }

