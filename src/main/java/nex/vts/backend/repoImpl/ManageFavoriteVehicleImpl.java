package nex.vts.backend.repoImpl;

import nex.vts.backend.controllers.CtrlLogin;
import nex.vts.backend.exceptions.AppCommonException;
import nex.vts.backend.models.responses.ManageFavoriteVehicle;
import nex.vts.backend.repositories.SetManageFavoriteVehicleRepo;
import oracle.jdbc.OracleTypes;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.Map;

@Service
public class ManageFavoriteVehicleImpl implements SetManageFavoriteVehicleRepo {

    private final Logger logger = LoggerFactory.getLogger(ManageFavoriteVehicleImpl.class);


    @Autowired
    private JdbcTemplate jdbcTemplete;

    SimpleJdbcCall getAllStatesJdbcCall;


    public ManageFavoriteVehicleImpl(DataSource dataSource) {

        this.getAllStatesJdbcCall = new SimpleJdbcCall(dataSource);
    }

    @Override
    public String setManageFavoriteVehicle(ManageFavoriteVehicle m) {
        String out;

        try {
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

               /* .execute(m.getP_TYPE(),
                        m.getProfileType(),
                        m.getProfileId(),
                        m.getParentId(),
                        m.getP_VEHICLE_ID(),
                        m.getP_FAVORITE_VALUE());*/




        return out;
    }
}
