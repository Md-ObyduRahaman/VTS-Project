package nex.vts.backend.repoImpl;

import nex.vts.backend.models.User;
import nex.vts.backend.repositories.UserRepo;
import oracle.jdbc.OracleTypes;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class UserRepoImpl implements UserRepo {

    SimpleJdbcCall getAllStatesJdbcCall;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JdbcTemplate jdbcTemplete;


    public UserRepoImpl(DataSource dataSource) {

        this.getAllStatesJdbcCall = new SimpleJdbcCall(dataSource);
    }

    @Override
    public List<User> findAll() {

        ArrayList<User> userArrayList = new ArrayList<>();
        try {
            Map<String, Object> result = getAllStatesJdbcCall.withProcedureName("get_data_users")
                    .declareParameters(new SqlOutParameter("p_result_cursor", OracleTypes.CURSOR))
                    .execute(100);

            JSONObject json = new JSONObject(result);
            String out = json.get("p_result_cursor").toString();
            JSONArray jsonArray = new JSONArray(out);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonData = jsonArray.getJSONObject(i);
                userArrayList.add(new User(
                        jsonData.optLong("USER_ID"),
                        jsonData.optInt("IS_ACTIVE"),
                        jsonData.optString("USERNAME"),
                        jsonData.optString("PASSWORD"),
                        jsonData.optString("EMAIL"),
                        jsonData.optString("ROLES")));
            }
        } catch (NumberFormatException e) {
            System.err.println("You are trying to pass wrong type of arguments ,please check arguments index position with procedure arguments index position, and try to pass write f DataType. Please check Error massage.\nError massage: " + e.getMessage());

        } catch (BadSqlGrammarException e) {
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

        return userArrayList;
    }

    @Override
    public Optional<User> findByUserName(String userName) {
        return jdbcTemplete.queryForObject(
                "select * from USERLOGIN where USERNAME = ?",
                new Object[]{userName},
                (rs, rowNum) ->
                        Optional.of(new User(
                                rs.getLong("USER_ID"),
                                rs.getInt("IS_ACTIVE"),
                                rs.getString("USERNAME"),
                                rs.getString("PASSWORD"),
                                rs.getString("EMAIL"),
                                rs.getString("ROLES")
                        ))
        );
    }

    @Override
    public boolean findById(Integer userID) {
        boolean hasRecord =
                jdbcTemplete
                        .query("select * from driverdetails WHERE driverid=?",
                                new Object[]{userID},
                                (ResultSet rs) -> {
                                    return rs.next();
                                }
                        );
        return hasRecord;

    }

    @Override
    public int save(User user) {
        user.setPASSWORD(passwordEncoder.encode(user.getPASSWORD()));
        return jdbcTemplete.update(
                "insert into USERLOGIN (USER_ID, IS_ACTIVE,USERNAME,PASSWORD,EMAIL,ROLES) values(?,?,?,?,?,?)",
                user.getUSER_ID(), user.getIS_ACTIVE(), user.getUSERNAME(), user.getPASSWORD(), user.getEMAIL(), user.getROLES());
    }
}
