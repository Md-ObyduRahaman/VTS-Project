package nex.vts.backend.repoImpl;

import nex.vts.backend.dbentities.NEX_CORPORATE_CLIENT;
import nex.vts.backend.models.responses.DriverInfoModel;
import nex.vts.backend.repositories.DriverInfoRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DriverInfoImpl implements DriverInfoRepo {
    private final Logger logger = LoggerFactory.getLogger(RepoNexCorporateClient.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Optional<DriverInfoModel> getCorporateClientProfile(int USERID) {
        logger.debug("Executing query to get client profile by client profile id: {}", USERID);
        return jdbcTemplate.queryForObject("SELECT ID,USERID,D_NAME,D_FNAME,D_LICENSE,D_ADDRESS,D_CELL,\n" +
                "TO_CHAR(TO_DATE(D_DOB, 'YYYYMMDD'), 'MM/DD/YYYY') D_DOB, LENGTH(DRIVER_PHOTO) DRIVER_HAS_PHOTO\n" +
                "FROM NEX_DRIVERINFO WHERE USERID=?", new Object[]{USERID}, (rs, rowNum) ->
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
