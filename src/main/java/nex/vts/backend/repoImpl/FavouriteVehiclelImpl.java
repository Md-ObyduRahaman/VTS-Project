package nex.vts.backend.repoImpl;


import nex.vts.backend.exceptions.AppCommonException;
import nex.vts.backend.models.responses.FavouriteVehiclelModel;
import nex.vts.backend.repositories.FavouriteVehiclelRepo;
import oracle.jdbc.xa.OracleXAException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.TransientDataAccessException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;


import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class FavouriteVehiclelImpl implements FavouriteVehiclelRepo {

    private final Logger logger = LoggerFactory.getLogger(FavouriteVehiclelImpl.class);


    @Autowired
    private JdbcTemplate jdbcTemplate;



    @Override
    public Optional<ArrayList<FavouriteVehiclelModel>> findNeededData(String limit, Integer offset, Integer GROUP_ID, Integer OPERATORID, Integer userType, Integer PARENT_PROFILE_ID)  {
       String ConcateSql ="";
        if (limit!="ALL"){
             ConcateSql = " OFFSET "+offset+
                    "ROWS FETCH FIRST "+limit+ " ROWS ONLY";
        }
         String sql=null;


        switch (userType) {
            case 1:

                 sql="SELECT a.VEHICLE_ID                          id,\n" +
                        "         a.USERID                              vehicle_name,\n" +
                        "         a.ENGIN                               engine_status,\n" +
                        "         a.SPEED                               speed,\n" +
                        "         b.FAVORITE                            is_favorite,\n" +
                        "         b.ICON_TYPE                           vehicle_icon_type,\n" +
                        "         b.CUSTOM_USERID                       user_defined_vehicle_name,\n" +
                        "         b.ICON_TYPE_ON_MAP,\n" +
                        "         b.ICON_TYPE_RUNNING,\n" +
                        "         b.ICON_TYPE_STOPPED,\n" +
                        "         b.ICON_TYPE_STATIONARY,\n" +
                        "         b.CAR_REG_NO                          registration_number,\n" +
                        "         b.CAR_COLOUR                          color,\n" +
                        "         b.CAR_VENDOR                          vendor,\n" +
                        "         b.CAR_MODEL                           model,\n" +
                        "         GPSNEXGP.GET_MAX_CAR_SPEED (b.ID)     max_speed\n" +
                        "    FROM nex_individual_temp a, nex_individual_client b\n" +
                        "   WHERE     a.VEHICLE_ID = b.ID\n" +
                        "         AND b.FAVORITE = 1\n" +
                        "         AND a.GROUP_ID = "+GROUP_ID+
                        "         AND a.OPERATORID = "+OPERATORID+
                        "         AND a.ACTIVATION = 1\n" +
                        "ORDER BY a.VEHICLE_ID ASC";

                break;
            case 2:
                sql=" SELECT a.VEHICLE_ID                 id,\n" +
                        "         a.USERID                     vehicle_name,\n" +
                        "         a.ENGIN                      engine_status,\n" +
                        "         a.SPEED                      speed,\n" +
                        "         b.FAVORITE                   is_favorite,\n" +
                        "         b.ICON_TYPE                  vehicle_icon_type,\n" +
                        "         b.CUSTOM_USERID              user_defined_vehicle_name,\n" +
                        "         b.ICON_TYPE_ON_MAP,\n" +
                        "         b.ICON_TYPE_RUNNING,\n" +
                        "         b.ICON_TYPE_STOPPED,\n" +
                        "         b.ICON_TYPE_STATIONARY,\n" +
                        "         b.CAR_REG_NO                 registration_number,\n" +
                        "         b.CAR_COLOUR                 color,\n" +
                        "         b.CAR_VENDOR                 vendor,\n" +
                        "         b.CAR_MODEL                  model,\n" +
                        "         GPSNEXGP.GET_MAX_CAR_SPEED (b.ID)     max_speed\n" +
                        "    FROM nex_individual_temp a, nex_individual_client b\n" +
                        "   WHERE     b.FAVORITE = 1\n" +
                        "         AND a.GROUP_ID = "+GROUP_ID+
                        "         AND a.OPERATORID = "+OPERATORID+
                        "         AND a.ACTIVATION = 1\n" +
                        "ORDER BY a.VEHICLE_ID ASC";
                break;
            case 3:
                sql="  SELECT a.VEHICLE_ID                 id,\n" +
                        "         a.USERID                     vehicle_name,\n" +
                        "         a.ENGIN                      engine_status,\n" +
                        "         a.SPEED                      speed,\n" +
                        "         b.FAVORITE                   is_favorite,\n" +
                        "         b.ICON_TYPE                  vehicle_icon_type,\n" +
                        "         b.CUSTOM_USERID              user_defined_vehicle_name,\n" +
                        "         b.ICON_TYPE_ON_MAP,\n" +
                        "         b.ICON_TYPE_RUNNING,\n" +
                        "         b.ICON_TYPE_STOPPED,\n" +
                        "         b.ICON_TYPE_STATIONARY,\n" +
                        "         b.CAR_REG_NO                 registration_number,\n" +
                        "         b.CAR_COLOUR                 color,\n" +
                        "         b.CAR_VENDOR                 vendor,\n" +
                        "         b.CAR_MODEL                  model,\n" +
                        "         GPSNEXGP.GET_MAX_CAR_SPEED (b.ID)     max_speed\n" +
                        "    FROM nex_individual_temp a, nex_individual_client b\n" +
                        "   WHERE     b.FAVORITE = 1\n" +
                        "         AND a.GROUP_ID = "+GROUP_ID +
                        "         AND a.OPERATORID = "+OPERATORID +
                        "         AND a.ACTIVATION = 1\n" +
                        "ORDER BY a.VEHICLE_ID ASC";
                break;

            default:
                sql="SELECT a.VEHICLE_ID                 id,\n" +
                        "         a.USERID                     vehicle_name,\n" +
                        "         a.ENGIN                      engine_status,\n" +
                        "         a.SPEED                      speed,\n" +
                        "         b.FAVORITE                   is_favorite,\n" +
                        "         b.ICON_TYPE                  vehicle_icon_type,\n" +
                        "         b.CUSTOM_USERID              user_defined_vehicle_name,\n" +
                        "         b.ICON_TYPE_ON_MAP,\n" +
                        "         b.ICON_TYPE_RUNNING,\n" +
                        "         b.ICON_TYPE_STOPPED,\n" +
                        "         b.ICON_TYPE_STATIONARY,\n" +
                        "         b.CAR_REG_NO                 registration_number,\n" +
                        "         b.CAR_COLOUR                 color,\n" +
                        "         b.CAR_VENDOR                 vendor,\n" +
                        "         b.CAR_MODEL                  model,\n" +
                        "         GPSNEXGP.GET_MAX_CAR_SPEED (b.ID)     max_speed\n" +
                        "    FROM nex_individual_temp a, nex_individual_client b\n" +
                        "   WHERE     a.VEHICLE_ID = b.id\n" +
                        "         AND VEHICLE_ID IN\n" +
                        "                 (SELECT TO_CHAR (VEHICLE_ID)\n" +
                        "                    FROM GPSNEXGP.NEX_EXTENDED_USER_VS_VEHICLE\n" +
                        "                   WHERE     PROFILE_ID = "+ GROUP_ID +
                        "                         AND PROFILE_TYPE = "+ userType +
                        "                         AND PARENT_PROFILE_ID = "+PARENT_PROFILE_ID +
                        "       )  AND b.OPERATORID = "+OPERATORID+
                        "         AND b.ACTIVATION = 1\n" +
                        "         AND b.FAVORITE = 1\n" +
                        "ORDER BY a.VEHICLE_ID ASC";
        }




        String finalSQL=sql.concat(ConcateSql);


        Optional<ArrayList<FavouriteVehiclelModel>> favouriteVehiclelList=Optional.empty();

        try {

            favouriteVehiclelList = Optional.of((ArrayList<FavouriteVehiclelModel>) jdbcTemplate.query(finalSQL,
                    BeanPropertyRowMapper.newInstance(FavouriteVehiclelModel.class)));
        }
        catch (BadSqlGrammarException e) {
            logger.trace("No Data found with userType is {}  Sql Grammar Exception", userType);
            throw new AppCommonException(4001 + "##Sql Grammar Exception");
        }catch (TransientDataAccessException f){
            logger.trace("No Data found with userType is {} network or driver issue or db is temporarily unavailable  ", userType);
            throw new AppCommonException(4002 + "##Network or driver issue or db is temporarily unavailable");
        }catch (CannotGetJdbcConnectionException g){
            logger.trace("No Data found with userType is {} could not acquire a jdbc connection  ", userType);
            throw new AppCommonException(4003 + "##A database connection could not be obtained");
        }


        if (favouriteVehiclelList.get().isEmpty())
        {
            return Optional.empty();
        }
        else {
            return favouriteVehiclelList;
        }
    }
}
