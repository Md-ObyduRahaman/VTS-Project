package nex.vts.backend.repoImpl;


import nex.vts.backend.models.responses.VehicleOthersInfoModel;

import nex.vts.backend.repositories.VehicleOthersInfoRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VehicleOthersInfoImpl implements VehicleOthersInfoRepo {

    private final Logger logger = LoggerFactory.getLogger(VehicleOthersInfoImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Optional<VehicleOthersInfoModel> getVehicleOthersInfo(Integer rowID) {
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
                            rs.getInt("IS_FAVORITE"), rs.getInt("VEHICLE_STATUS"),
                            rs.getInt("IS_MULTIPLE_NOTIFICATION_ALLOW"),
                            rs.getInt("IS_SAFE_MODE_ACTIVE"), rs.getInt("MAX_CAR_SPEED")
                    )
            ).stream().findFirst();
        } catch (Exception e) {
            if (e instanceof EmptyResultDataAccessException) {
                logger.trace("No user found with rowID {} on VTS_LOGIN_USER tbl", rowID);
                return userObj;
            }
        }

        return userObj;
        //return Optional.empty();
    }
}
