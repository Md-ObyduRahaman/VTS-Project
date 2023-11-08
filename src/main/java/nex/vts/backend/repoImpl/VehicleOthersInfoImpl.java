package nex.vts.backend.repoImpl;


import nex.vts.backend.exceptions.AppCommonException;
import nex.vts.backend.models.responses.FavouriteVehiclelModel;
import nex.vts.backend.models.responses.VehicleOthersInfoModel;

import nex.vts.backend.repositories.VehicleOthersInfoRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.TransientDataAccessException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class VehicleOthersInfoImpl implements VehicleOthersInfoRepo {
    private final short API_VERSION = 1;

    private final Logger logger = LoggerFactory.getLogger(VehicleOthersInfoImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Optional<VehicleOthersInfoModel> getVehicleOthersInfo(Integer rowID,Integer deviceType) {
        logger.trace("Executing query to find rowID by rowID: {}", rowID);
        Optional<VehicleOthersInfoModel> userObj = Optional.empty();
        String query = "SELECT FAVORITE                is_favorite,\n" +
                "       IND_PASS,\n" +
                "       IND_LOGIN               vehicle_status,\n" +
                "       CELL_PHONE,\n" +
                "       EMAIL,\n" +
                "       IS_MULTIPLE_N_ALLOW     is_multiple_notification_allow,\n" +
                "       IS_SAFE_MODE_ACTIVE,\n" +
                "       MAX_CAR_SPEED\n" +
                "  FROM NEX_INDIVIDUAL_CLIENT A, NEX_DRIVERINFO B\n" +
                " WHERE A.ID = B.USERID AND A.ID = ?";

        try {
            userObj = jdbcTemplate.query(query, new Object[]{rowID}, (rs, rowNum) ->
                    new VehicleOthersInfoModel(rs.getString("IND_PASS"),
                            rs.getString("CELL_PHONE"),
                            rs.getString("EMAIL"),
                            rs.getBoolean("IS_FAVORITE"), rs.getInt("VEHICLE_STATUS"),
                            rs.getBoolean("IS_MULTIPLE_NOTIFICATION_ALLOW"),
                            rs.getInt("IS_SAFE_MODE_ACTIVE"), rs.getInt("MAX_CAR_SPEED")
                    )
            ).stream().findFirst();
        }
        catch (BadSqlGrammarException e) {
            logger.trace("No Data found with rowID is {}  Sql Grammar Exception", rowID);
            throw new AppCommonException(4001 + "##Sql Grammar Exception" + deviceType + "##" + API_VERSION);
        }catch (TransientDataAccessException f){
            logger.trace("No Data found with rowID is {} network or driver issue or db is temporarily unavailable  ", rowID);
            throw new AppCommonException(4002 + "##Network or driver issue or db is temporarily unavailable" + deviceType + "##" + API_VERSION);
        }catch (CannotGetJdbcConnectionException g){
            logger.trace("No Data found with rowID is {} could not acquire a jdbc connection  ", rowID);
            throw new AppCommonException(4003 + "##A database connection could not be obtained" + deviceType + "##" + API_VERSION);
        }

        return userObj;
        //return Optional.empty();
    }
}
