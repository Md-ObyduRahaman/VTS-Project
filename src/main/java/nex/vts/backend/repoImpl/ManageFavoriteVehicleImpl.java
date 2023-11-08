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

    private final short API_VERSION = 1;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    SimpleJdbcCall getAllStatesJdbcCall;


    public ManageFavoriteVehicleImpl(DataSource dataSource) {

        this.getAllStatesJdbcCall = new SimpleJdbcCall(dataSource);
    }

    @Override
    public String setManageFavoriteVehicle(ManageFavoriteVehicle m,Integer deviceType) {
        String out;

        String p_type = "SetFavorite";
        String p_profile_type = "1";
        String  p_profile_id = "7988";
        String p_parent_profile_id = "7988";
        String  p_vehicle_id = "30319";
        String p_favorite_value = "1";
        String v_res="p_response" ;




    String sql  = "begin manage_favorite_vehicle ('SetFavorite'"+","+p_profile_type+","+p_profile_id+","+p_parent_profile_id+","+p_vehicle_id+","+p_favorite_value+","+v_res+"); end;";


        System.out.println("STAT: "+sql);
      //  System.exit(0);

        Optional<ArrayList<SpeedDataResponse>> speedDataResponses = Optional.empty();



            out = String.valueOf(Optional.of((ArrayList<SpeedDataResponse>) jdbcTemplate.query(sql,
                    BeanPropertyRowMapper.newInstance(SpeedDataResponse.class))));

       return out;
        }
}


