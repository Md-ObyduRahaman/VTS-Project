package nex.vts.backend.repoImpl;

import nex.vts.backend.dbentities.NEX_CORPORATE_CLIENT;
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
    String sql = null;

    public DriverInfoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<DriverInfoModel> findDriverInfo(Integer USERID) {
        logger.debug("Executing query to get client profile by client user id: {}", USERID);

        return jdbcTemplate.queryForObject("SELECT USERID FROM NEX_DRIVERINFO WHERE USERID=?", new Object[]{USERID}, (rs, rowNum) ->
                Optional.of(new DriverInfoModel(
                        rs.getString("ID"),
                        rs.getString("USERID"),
                        rs.getString("D_NAME"),
                        rs.getString("D_FNAME"),
                        rs.getString("D_LICENSE"),
                        rs.getString("D_ADDRESS"),
                        rs.getString("D_CELL"),
                        rs.getString("D_DOB"),
                        rs.getBoolean("DRIVER_HAS_PHOTO")
                ))
        );
    }
}

        /*

        Optional<DriverInfoModel> getAllDriverInfo = Optional.empty();


        try {

             getAllDriverInfo = Optional.of((DriverInfoModel) jdbcTemplate.query(sql,
                    BeanPropertyRowMapper.newInstance(DriverInfoModel.class)));
        } catch (BadSqlGrammarException e) {
            logger.trace("No Data found with userId is {}  Sql Grammar Exception", USERID);
            throw new AppCommonException(4001 + "##Sql Grammar Exception");
        } catch (TransientDataAccessException f) {
            logger.trace("No Data found with userId is {} network or driver issue or db is temporarily unavailable  ", USERID);
            throw new AppCommonException(4002 + "##Network or driver issue or db is temporarily unavailable");
        } catch (CannotGetJdbcConnectionException g) {
            logger.trace("No Data found with userId is {} could not acquire a jdbc connection  ", USERID);
            throw new AppCommonException(4003 + "##A database connection could not be obtained");
        }
    }
}
*/



