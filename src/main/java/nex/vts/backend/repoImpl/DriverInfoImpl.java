package nex.vts.backend.repoImpl;

import nex.vts.backend.dbentities.NEX_CORPORATE_CLIENT;
import nex.vts.backend.exceptions.AppCommonException;
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
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
@Repository
public class DriverInfoImpl implements DriverInfoRepo {
    private final Logger logger = LoggerFactory.getLogger(DriverInfoModel.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public DriverInfoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<DriverInfoModel> findDriverInfo(Integer USERID) {
        logger.debug("Executing query to get client profile by client user id: {}", USERID);

        Optional<DriverInfoModel> getDriverInfo;

        String sql = "SELECT USERID, ID, D_NAME, D_FNAME, D_LICENSE, D_ADDRESS, D_CELL, D_DOB, DRIVER_PHOTO FROM NEX_DRIVERINFO WHERE USERID="+USERID;


        try
        {

            getDriverInfo = Optional.of((DriverInfoModel) jdbcTemplate.queryForObject(sql,
                    BeanPropertyRowMapper.newInstance(DriverInfoModel.class)));
        }
        catch (BadSqlGrammarException e) {
            logger.trace("No Data found with userId is {}  Sql Grammar Exception", USERID);
            throw new AppCommonException(4001 + "##Sql Grammar Exception#1#1");
        }
        catch (TransientDataAccessException f) {
            logger.trace("No Data found with userId is {} network or driver issue or db is temporarily unavailable  ", USERID);
            throw new AppCommonException(4002 + "##Network or driver issue or db is temporarily unavailable");
        }
        catch (CannotGetJdbcConnectionException g) {
            logger.trace("No Data found with userId is {} could not acquire a jdbc connection  ", USERID);
            throw new AppCommonException(4003 + "##A database connection could not be obtained");
        }

        if (getDriverInfo.isEmpty()) {
            return Optional.empty();
        } else {
            return getDriverInfo;
        }
    }
}

