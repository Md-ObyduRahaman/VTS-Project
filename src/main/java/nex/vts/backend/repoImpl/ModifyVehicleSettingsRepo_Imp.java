package nex.vts.backend.repoImpl;

import nex.vts.backend.exceptions.AppCommonException;
import nex.vts.backend.repositories.ModifyVehicleSettings_Repo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;

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
                                      String cellPhone, String email, int isFavourite,String schemaName) throws SQLException {

        Connection connection = dataSource.getConnection();

        String storeProcedure = "{call ".concat(schemaName).concat("call modify_vehicle_profile_ex(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");

        CallableStatement statement = connection.prepareCall(storeProcedure);

        statement.setString(1,"ChangeAll");
        statement.setInt(2,profileType);
        statement.setInt(3,profileId);
        statement.setInt(4,parentProfileId);
        statement.setInt(5,vehicleId);
        statement.setString(6,maxSpeed);
        statement.setString(7,cellPhone);
        statement.setString(8,email);
        statement.setInt(9,isFavourite);
        statement.registerOutParameter(10, Types.VARCHAR);

        try {

            boolean result = statement.execute();
            outResponse = statement.getString(11);

            if (result){

                ResultSet resultSet = statement.getResultSet();
            }
        }
        catch (Exception e){

            logger.error(e.getMessage());
            throw new AppCommonException(600 + "##Required parameter is missing" + profileId + "##" + API_VERSION);

        }finally {

            statement.close();
            connection.close();
        }

        return outResponse;
    }

}
