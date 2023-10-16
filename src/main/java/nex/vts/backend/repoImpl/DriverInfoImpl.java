package nex.vts.backend.repoImpl;

import nex.vts.backend.controllers.DriverInfoController;
import nex.vts.backend.dbentities.NEX_CORPORATE_CLIENT;
import nex.vts.backend.dbentities.NEX_INDIVIDUAL_CLIENT;
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
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class DriverInfoImpl implements DriverInfoRepo {
    private final Logger logger = LoggerFactory.getLogger(RepoNexCorporateClient.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int findDriverInfo(Integer userID) {
        int DriverInfo =
                jdbcTemplate.query("select * from FROM NEX_DRIVERINFO WHERE USERID=?",
                                new Object[]{userID},
                                (ResultSet rs) -> {
                                    return rs.getInt("USER_ID");
                                }
                        );
        return DriverInfo;

    }


}

