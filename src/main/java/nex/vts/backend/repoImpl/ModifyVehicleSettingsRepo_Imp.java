package nex.vts.backend.repoImpl;

import nex.vts.backend.exceptions.AppCommonException;
import nex.vts.backend.models.responses.VehicleConfigModel;
import nex.vts.backend.repositories.ModifyVehicleSettings_Repo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.net.ConnectException;
import java.sql.*;
import java.util.Optional;

@Repository
public class ModifyVehicleSettingsRepo_Imp implements ModifyVehicleSettings_Repo {
    Logger logger = LoggerFactory.getLogger(ModifyVehicleSettingsRepo_Imp.class);
    private final short API_VERSION = 1;
    private final JdbcTemplate jdbcTemplate;
    private final DataSource dataSource;
    String outResponse = null;

    @Autowired
    public ModifyVehicleSettingsRepo_Imp(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.dataSource = dataSource;
    }

    @Override
    public String modifyVehicleSettings(Integer profileType, Integer profileId,
                                        Integer parentProfileId, Integer vehicleId, String maxSpeed,
                                        String cellPhone, String email, int isFavourite, String schemaName) throws SQLException {

        Connection connection = dataSource.getConnection();

        String storeProcedure = "{call ".concat(schemaName).concat("modify_vehicle_profile_ex(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");

        CallableStatement statement = connection.prepareCall(storeProcedure);

        statement.setString(1, "ChangeAll");
        statement.setInt(2, profileType);
        statement.setInt(3, profileId);
        statement.setInt(4, parentProfileId);
        statement.setInt(5, vehicleId);
        statement.setString(6, maxSpeed);
        statement.setString(7, cellPhone);
        statement.setString(8, email);
        statement.setInt(9, isFavourite);
        statement.registerOutParameter(10, Types.VARCHAR);

        try {

            boolean result = statement.execute();
            outResponse = statement.getString(10);

            if (result) {

                ResultSet resultSet = statement.getResultSet();
            }
        } catch (Exception e) {

            logger.error(e.getMessage());
            throw new AppCommonException(600 + "##Required parameter is missing" + profileId + "##" + API_VERSION);

        } finally {

            statement.close();
            connection.close();
        }

        return outResponse;
    }


    @Retryable(retryFor = {ConnectException.class, DataAccessException.class},
            maxAttempts = 5, backoff = @Backoff(delay = 2000, multiplier = 2))
    @Override
    public VehicleConfigModel getVehicleSettings(Integer vehicleId) {

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
        try {

            return (VehicleConfigModel) jdbcTemplate.queryForObject(query, new Object[]{vehicleId}, new RowMapper<VehicleConfigModel>() {
                @Override
                public VehicleConfigModel mapRow(ResultSet rs, int rowNum) throws SQLException {

                    return new VehicleConfigModel(

                            rs.getInt("ID"),
                            rs.getString("USERID"),
                            rs.getString("CELL_PHONE"),
                            rs.getString("EMAIL"),
                            rs.getInt("FAVORITE"),
                            rs.getString("MAX_CAR_SPEED")
                    );
                }
            });

        } catch (Exception e) {

            e.getMessage();
            throw new AppCommonException(777 + "##Required field is missing ##" + vehicleId + API_VERSION);
        }

    }
}
