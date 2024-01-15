package nex.vts.backend.repoImpl;

import nex.vts.backend.exceptions.AppCommonException;
import nex.vts.backend.models.responses.AccountSummaryInfo;
import nex.vts.backend.models.responses.VehicleStateInfoOra;
import nex.vts.backend.models.vehicle.rowMapper.VehicleStateInfo_RowMapper;
import nex.vts.backend.models.vehicle.rowMapper.Vehicle_Details_RowMapper;
import nex.vts.backend.repositories.VehicleStateRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.TransientDataAccessException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VehicleStateImpl implements VehicleStateRepo {


    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    Environment environment;
    private final Logger logger = LoggerFactory.getLogger(VehicleStateImpl.class);

    @Override
    public  Optional<ArrayList<VehicleStateInfoOra>>  findVehicleStateInfoInfo(Integer parentProfileId) {
        logger.debug("Executing query to get client profile by client parentProfileId: {}", parentProfileId);

        String schemaName = environment.getProperty("application.profiles.shcemaName");




        String sql = "select \n" +
                "    ROWNUM ROWNO, \n" +
                "    VEHICLE_ID, ENGIN, LAT, LON, VDATE, VEH_MAINTENANCE, ICON_TYPE\n" +
                "FROM\n" +
                "   (\n" +
                "    select \n" +
                "        ROWNUM ROWNO, \n" +
                "        VEHICLE_ID, ENGIN, LAT, LON, \n" +
                "        to_char(to_date(VDATE, 'YYYY-MM-DD HH24:MI:SS')-2 / 24, 'YYYY-MM-DD HH24:MI:SS') VDATE,\n" +
                "        "+schemaName+"get_vehicle_service_stat(VEHICLE_ID) as VEH_MAINTENANCE,\n" +
                "        ICON_TYPE\n" +
                "    FROM nex_individual_temp \n" +
                "    where group_id = '"+parentProfileId+"' \n" +
                "    order by ID asc\n" +
                ")";




        Optional<ArrayList<VehicleStateInfoOra>>  vehicleStateInfoList;

        try {

            vehicleStateInfoList = Optional.of((ArrayList<VehicleStateInfoOra>) jdbcTemplate.query(sql,new VehicleStateInfo_RowMapper() ,new Object[]{}));

        }
        catch (BadSqlGrammarException e) {
            e.printStackTrace();
            logger.trace("No Data found with parentProfileId is {}  Sql Grammar Exception", parentProfileId);
            throw new AppCommonException(4001 + "##Sql Grammar Exception##1##1");
        } catch (TransientDataAccessException f) {
            logger.trace("No Data found with parentProfileId is {} network or driver issue or db is temporarily unavailable  ", parentProfileId);
            throw new AppCommonException(4002 + "##Network or driver issue or db is temporarily unavailable");
        } catch (CannotGetJdbcConnectionException g) {
            logger.trace("No Data found with parentProfileId is {} could not acquire a jdbc connection  ", parentProfileId);
            throw new AppCommonException(4003 + "##A database connection could not be obtained");
        }

        if (vehicleStateInfoList.isEmpty()) {
            return Optional.empty();
        } else {
            return vehicleStateInfoList;
        }
    }


}
