package nex.vts.backend.repoImpl;

import nex.vts.backend.controllers.DriverInfoController;
import nex.vts.backend.dbentities.NEX_CORPORATE_CLIENT;
import nex.vts.backend.dbentities.NEX_INDIVIDUAL_CLIENT;
import nex.vts.backend.exceptions.AppCommonException;
import nex.vts.backend.models.responses.AccountSummary;
import nex.vts.backend.models.responses.DriverInfoModel;
import nex.vts.backend.models.responses.GetExpansesModel;
import nex.vts.backend.repositories.DriverInfoRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.TransientDataAccessException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class DriverInfoImpl implements DriverInfoRepo {
    private final Logger logger = LoggerFactory.getLogger(RepoNexCorporateClient.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;
    String sql = null;

    public Optional<DriverInfoModel> findDriverInfo(Integer userID) {
        String sql = "select * from NEX_DRIVERINFO WHERE USERID=?";
        Optional<DriverInfoModel> driverInfoOptional = Optional.empty();

        try {
            DriverInfoModel driverInfo = jdbcTemplate.queryForObject(
                    sql,
                    new Object[]{userID},
                    (ResultSet rs, int rowNum) -> {
                        DriverInfoModel model = new DriverInfoModel();
                        // Populate model with data from ResultSet
                        return model;
                    }
            );

            driverInfoOptional = Optional.ofNullable(driverInfo);

            try {

                driverInfoOptional = Optional.of(DriverInfoModel) jdbcTemplate.query(sql,
                        BeanPropertyRowMapper.newInstance(DriverInfoModel.class));
            } catch (BadSqlGrammarException e) {
                logger.trace("No Data found with profileId is {}  Sql Grammar Exception", userID);
                throw new AppCommonException(4001 + "##Sql Grammar Exception");
            } catch (TransientDataAccessException f) {
                logger.trace("No Data found with profileId is {} network or driver issue or db is temporarily unavailable  ", userID);
                throw new AppCommonException(4002 + "##Network or driver issue or db is temporarily unavailable");
            } catch (CannotGetJdbcConnectionException g) {
                logger.trace("No Data found with profileId is {} could not acquire a jdbc connection  ", userID);
                throw new AppCommonException(4003 + "##A database connection could not be obtained");
            }

            if (driverInfoOptional.get().isEmpty()) {
                return Optional.empty();
            } else {
                return DriverInfo;
            }


        }

    }
}