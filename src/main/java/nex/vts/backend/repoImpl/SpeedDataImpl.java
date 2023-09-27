package nex.vts.backend.repoImpl;

import nex.vts.backend.exceptions.AppCommonException;

import nex.vts.backend.models.responses.SpeedDataResponse;
import nex.vts.backend.repositories.SpeedDataRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.TransientDataAccessException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class SpeedDataImpl implements SpeedDataRepo {

    private final Logger logger = LoggerFactory.getLogger(SpeedDataImpl.class);


    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Override
    public Optional<ArrayList<SpeedDataResponse>> getSpeedDataForhr(String finalToTime, String finalFromTime, Integer vehicleId) {

        String sql="SELECT ID,\n" +
                "         TIME_IN_NUMBER              date_time,\n" +
                "         POSITION,\n" +
                "         SPEED,\n" +
                "         (SELECT USERID\n" +
                "            FROM NEX_INDIVIDUAL_CLIENT\n" +
                "           WHERE ID = "+vehicleId+")    VEHICLE_NAME\n" +
                "    FROM (SELECT ID,\n" +
                "                 TIME_IN_NUMBER,\n" +
                "                 POSITION,\n" +
                "                 SPEED\n" +
                "            FROM nex_historyrecv\n" +
                "           WHERE vehicleid = TO_CHAR ( "+vehicleId+"))\n" +
                "   WHERE TIME_IN_NUMBER BETWEEN "+finalFromTime+" AND "+finalToTime+"\n" +
                "ORDER BY id ASC";

        Optional<ArrayList<SpeedDataResponse>> speedDataResponses = Optional.empty();

        try {

            speedDataResponses = Optional.of((ArrayList<SpeedDataResponse>) jdbcTemplate.query(sql,
                    BeanPropertyRowMapper.newInstance(SpeedDataResponse.class)));
        } catch (BadSqlGrammarException e) {
            logger.trace("No Data found with vehicleId is {}  Sql Grammar Exception", vehicleId);
            throw new AppCommonException(4001 + "##Sql Grammar Exception");
        } catch (TransientDataAccessException f) {
            logger.trace("No Data found with profileId is {} network or driver issue or db is temporarily unavailable  ", vehicleId);
            throw new AppCommonException(4002 + "##Network or driver issue or db is temporarily unavailable");
        } catch (CannotGetJdbcConnectionException g) {
            logger.trace("No Data found with profileId is {} could not acquire a jdbc connection  ", vehicleId);
            throw new AppCommonException(4003 + "##A database connection could not be obtained");
        }

        if (speedDataResponses.get().isEmpty())
        {
            return Optional.empty();
        }
        else {
            return speedDataResponses;
        }
    }
}
