package nex.vts.backend.repoImpl;

import nex.vts.backend.exceptions.AppCommonException;
import nex.vts.backend.models.responses.VehicleCurrentLocation;
import nex.vts.backend.repositories.TrackNowRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Repository
public class TrackNowRepository_Imp implements TrackNowRepository {
    private final short API_VERSION = 1;
    Logger logger = LoggerFactory.getLogger(TrackNowRepository_Imp.class);
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public TrackNowRepository_Imp(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<VehicleCurrentLocation> getCurrentLocation(Integer vehicleId) {

        String getQuery = "select a.ID                 ID," +
                "       a.LAT                LAT," +
                "       a.LON                LONGS," +
                "       a.SPEED              SPEED," +
                "       get_track_data_head(a.max_id)                 HEAD," +
                "       a.vdate              TIME_STAMP," +
                "       a.vdate              DATETIME," +
                "       a.ENGIN              ENGIN" +
                "from nex_individual_temp a,nex_individual_client b" +
                "where a.vehicle_id = b.id" +
                "and b.id = ?";

        try
        {
            return Optional.of(jdbcTemplate.queryForObject(getQuery, new RowMapper<VehicleCurrentLocation>() {

                @Override
                public VehicleCurrentLocation mapRow(ResultSet rs, int rowNum) throws SQLException {

                    return new VehicleCurrentLocation(

                            rs.getInt("ID"),
                            rs.getDouble("LAT"),
                            rs.getDouble("LONGS"),
                            rs.getDouble("SPEED"),
                            rs.getString("HEAD"),
                            rs.getString("TIME_STAMP"),
                            rs.getString("DATETIME"),
                            rs.getString("ENGIN")
                    );
                }
            },new Object[]{vehicleId}));
        }catch (Exception e){

            logger.error(e.getMessage());
            throw new AppCommonException(404 + "##Data Not Found##" + vehicleId + "##"+API_VERSION);
        }


    }
}
