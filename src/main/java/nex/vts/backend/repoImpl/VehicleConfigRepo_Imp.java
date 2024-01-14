package nex.vts.backend.repoImpl;

import nex.vts.backend.exceptions.AppCommonException;
import nex.vts.backend.models.responses.VehicleConfigModel;
import nex.vts.backend.repositories.VehicleConfig_Repo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Repository;

import java.net.ConnectException;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class VehicleConfigRepo_Imp implements VehicleConfig_Repo {

    private Logger logger = LoggerFactory.getLogger(VehicleConfigRepo_Imp.class);
    private final short API_VERSION = 1;
    private JdbcTemplate jdbcTemplate;

    @Retryable(retryFor = {ConnectException.class, DataAccessException.class},
            maxAttempts = 5, backoff = @Backoff(delay = 2000, multiplier = 2))
    @Override
    public Object getVehicleSetings(Integer vehicleId) {

        String query = "SELECT v.ID             ID,\n" +
                "       v.USERID         USERID,\n" +
                "       v.CELL_PHONE     CELL_PHONE,\n" +
                "       v.EMAIL          EMAIL,\n" +
                "       v.FAVORITE       FAVORITE,\n" +
                "       d.MAX_CAR_SPEED  MAX_CAR_SPEED\n" +
                "FROM nex_individual_client v,\n" +
                "     NEX_DRIVERINFO d\n" +
                "where v.ID = ?\n" +
                "  and v.ID = d.USERID";
        try{

            return jdbcTemplate.query(query,new Object[]{vehicleId}, (rs, rowNum) -> new VehicleConfigModel(

                    rs.getInt("ID"),
                    rs.getString("USERID"),
                    rs.getString("CELL_PHONE"),
                    rs.getString("EMAIL"),
                    rs.getBoolean("FAVORITE"),
                    rs.getString("MAX_CAR_SPEED")
            ));

        }catch (Exception e){

            e.getMessage();
            throw new AppCommonException(777 + "##Required field is missing ##"+vehicleId + API_VERSION);
        }
    }


    @Override
    public void setVehicleSetings(String cellPhone, String email, String maxCarSpeed, boolean isFavourite) {

    }
}
