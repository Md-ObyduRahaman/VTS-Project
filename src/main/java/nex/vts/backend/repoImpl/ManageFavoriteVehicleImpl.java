package nex.vts.backend.repoImpl;

import nex.vts.backend.controllers.CtrlLogin;
import nex.vts.backend.exceptions.AppCommonException;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import nex.vts.backend.models.responses.ManageFavoriteVehicle;
import nex.vts.backend.models.responses.SpeedDataResponse;
import nex.vts.backend.repositories.SetManageFavoriteVehicleRepo;
import oracle.jdbc.OracleTypes;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.TransientDataAccessException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class ManageFavoriteVehicleImpl implements SetManageFavoriteVehicleRepo {

    private final Logger logger = LoggerFactory.getLogger(ManageFavoriteVehicleImpl.class);

    @Value("${spring.datasource.url}")
    private String jdbcUrl;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    SimpleJdbcCall getAllStatesJdbcCall;


    public ManageFavoriteVehicleImpl(DataSource dataSource) {

        this.getAllStatesJdbcCall = new SimpleJdbcCall(dataSource);
    }

    @Override
    public String setManageFavoriteVehicle(ManageFavoriteVehicle m) {
        String out;

        //String sql="call MANAGE_FAVORITE_VEHICLE("+"+'SetFavorite'+"+","+45+","+25+","+15+","+15+","+35+","+"'l'"+")";
        String p_type = "SetFavorite";
        String p_profile_type = "1";
        String  p_profile_id = "7988";
        String p_parent_profile_id = "7988";
        String  p_vehicle_id = "30319";
        String p_favorite_value = "1";
        String v_res="p_response" ;




/*

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection connection = DriverManager.getConnection(jdbcUrl, username, password);

           // String sql = "begin manage_favorite_vehicle (?, ?, ?, ?, ?, ?); end;";
           // String sql = "begin manage_favorite_vehicle (?, ?, ?, ?, ?, ?); end;";
            //String sql="begin MANAGE_FAVORITE_VEHICLE("+"+'SetFavorite'+"+","+45+","+25+","+15+","+15+","+35+","+")";
                //   String sql = "begin manage_favorite_vehicle ('SetFavorite', 3, 25, 15, 15, 0,p_response); end;";
              String sql  = "begin manage_favorite_vehicle ("+p_type+","+p_profile_type+","+p_profile_id+","+p_parent_profile_id+","+p_vehicle_id+","+p_favorite_value+",p_response); end;";

            System.out.println(sql);
            System.exit(0);

            CallableStatement callableStatement = connection.prepareCall(sql);

            // Set the parameters
            callableStatement.setString(1, "SetFavorite");
            callableStatement.setInt(2, 45);
            callableStatement.setInt(3, 25);
            callableStatement.setInt(4, 15);
            callableStatement.setInt(5, 15);
            callableStatement.setInt(6, 35);

            System.out.println("STAT: "+callableStatement);
            System.exit(0);

            out = String.valueOf(callableStatement.execute());

            callableStatement.close();
            connection.close();
            return out;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
       return null;
    }
}*/

       // String sql  = "begin manage_favorite_vehicle (p_type, p_profile_type, p_profile_id, p_parent_profile_id, p_vehicle_id, p_favorite_value, p_response); end;";


      //  String sql  = "begin manage_favorite_vehicle ("+p_type+","+p_profile_type+","+p_profile_id+","+p_parent_profile_id+","+p_vehicle_id+","+p_favorite_value+","+"); end;";
       // String sql  = "begin manage_favorite_vehicle ("+'p_type'+","+45+","+25+","+15+","+15+","+35+"); end;";
     /*   String sql = "begin manage_favorite_vehicle ('SetFavorite', 45, 25, 15, 15, 35); end;";

        System.out.println(sql);*/

    String sql  = "begin manage_favorite_vehicle ('SetFavorite'"+","+p_profile_type+","+p_profile_id+","+p_parent_profile_id+","+p_vehicle_id+","+p_favorite_value+","+v_res+"); end;";


        System.out.println("STAT: "+sql);
      //  System.exit(0);

        Optional<ArrayList<SpeedDataResponse>> speedDataResponses = Optional.empty();

        try {

            out = String.valueOf(Optional.of((ArrayList<SpeedDataResponse>) jdbcTemplate.query(sql,
                    BeanPropertyRowMapper.newInstance(SpeedDataResponse.class))));
        } catch (BadSqlGrammarException e) {
            e.printStackTrace();
            logger.trace("No Data found with vehicleId is {}  Sql Grammar Exception", m.getP_VEHICLE_ID());
            throw new AppCommonException(4001 + "##Sql Grammar Exception");
        } catch (TransientDataAccessException f) {
            logger.trace("No Data found with profileId is {} network or driver issue or db is temporarily unavailable  ", m.getP_VEHICLE_ID());
            throw new AppCommonException(4002 + "##Network or driver issue or db is temporarily unavailable");
        } catch (CannotGetJdbcConnectionException g) {
            logger.trace("No Data found with profileId is {} could not acquire a jdbc connection  ", m.getP_VEHICLE_ID());
            throw new AppCommonException(4003 + "##A database connection could not be obtained");
        }
       return out;
        }
}



        /*try {
            Map<String, Object> result = getAllStatesJdbcCall.withProcedureName("MANAGE_FAVORITE_VEHICLE")
                    .declareParameters(new SqlOutParameter("p_response", OracleTypes.CURSOR))
                    .execute("SetFavorite",45,54,45,0,0,"l");
            JSONObject json = new JSONObject(result);
             out = json.get("p_response").toString();
        }
        catch (BadSqlGrammarException e){
            e.printStackTrace();
            logger.trace("wrong number or types of arguments in call to 'MANAGE_FAVORITE_VEHICLE getP_VEHICLE_ID is {} ", m.getP_VEHICLE_ID());
            throw new AppCommonException(4004 + "##wrong number or types of arguments in call to 'MANAGE_FAVORITE_VEHICLE'");

        }

               *//* .execute(m.getP_TYPE(),
                        m.getProfileType(),
                        m.getProfileId(),
                        m.getParentId(),
                        m.getP_VEHICLE_ID(),
                        m.getP_FAVORITE_VALUE());*//*




        return out;
    }*/

