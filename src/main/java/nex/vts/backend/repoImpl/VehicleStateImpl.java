package nex.vts.backend.repoImpl;

import nex.vts.backend.exceptions.AppCommonException;
import nex.vts.backend.models.responses.VehicleStateInfoOra;
import nex.vts.backend.models.vehicle.rowMapper.VehicleStateInfo_RowMapper;
import nex.vts.backend.repositories.VehicleStateRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.TransientDataAccessException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class VehicleStateImpl implements VehicleStateRepo {


    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    Environment environment;
    @Autowired
    private TransactionTemplate transactionTemplate;

    private final Logger logger = LoggerFactory.getLogger(VehicleStateImpl.class);

    @Override
    public Optional<ArrayList<VehicleStateInfoOra>> findVehicleStateInfoInfo(Integer parentProfileId,Integer userType,Integer userId,String SPECIFIC_VEHICLE_ID,int offSet) {
        logger.debug("Executing query to get client profile by client parentProfileId: {}", parentProfileId);

        String schemaName = environment.getProperty("application.profiles.shcemaName");
        String sql=null;
        int limit=15;

        if(userType==1) {

            sql = "SELECT  " +
                    "    ROWNUM ROWNO,  " +
                    "    ID,  " +
                    "    VEHICLE_ID,  " +
                    "    ENGIN,  " +
                    "    LAT,  " +
                    "    LON,  " +
                    "    VDATE,  " +
                    "    VEH_MAINTENANCE,  " +
                    "    ICON_TYPE,GET_VEHICLE_NAME(0, VEHICLE_ID) as VEHICLE_NAME " +
                    "FROM ( " +
                    "    SELECT  " +
                    "        ROWNUM ROWNO,  " +
                    "        t.ID,  " +
                    "        t.VEHICLE_ID,  " +
                    "        t.ENGIN,  " +
                    "        t.LAT,  " +
                    "        t.LON,  " +
                    "        TO_CHAR(TO_DATE(t.VDATE, 'YYYY-MM-DD HH24:MI:SS')-2 / 24, 'YYYY-MM-DD HH24:MI:SS') AS VDATE, " +
                    "        "+schemaName+"GET_VEHICLE_SERVICE_STAT(t.VEHICLE_ID) AS VEH_MAINTENANCE, " +
                    "        t.ICON_TYPE " +
                    "    FROM  " +
                    "        "+schemaName+"NEX_INDIVIDUAL_TEMP t " +
                    "    WHERE  " +
                    "        t.GROUP_ID = "+parentProfileId+"  " +
                    "        AND ( " +
                    "            t.VEHICLE_ID = "+SPECIFIC_VEHICLE_ID+" " +
                    "            OR "+SPECIFIC_VEHICLE_ID+" IS NULL " +
                    "        ) " +
                    " " +
                    "ORDER BY  " +
                    "    ID ASC \n" +
                    "    OFFSET "+offSet+" ROWS \n" +
                    "    FETCH NEXT "+limit+" ROWS ONLY \n" +
                    ")";
        }else if(userType==2) {
            sql="select  " +
                    "    ROWNUM ROWNO,  " +
                    "    VEHICLE_ID, ENGIN, LAT, LON, VDATE, VEH_MAINTENANCE, ICON_TYPE, " +
                    "    DEPT_ID,GET_VEHICLE_NAME(0, VEHICLE_ID) as VEHICLE_NAME " +
                    "FROM " +
                    "   ( " +
                    "    select  " +
                    "        ROWNUM ROWNO,  " +
                    "        t.VEHICLE_ID, t.ENGIN, t.LAT, t.LON,  " +
                    "        to_char(to_date(t.VDATE, 'YYYY-MM-DD HH24:MI:SS')-2 / 24, 'YYYY-MM-DD HH24:MI:SS') as VDATE, " +
                    "        "+schemaName+"get_vehicle_service_stat(t.VEHICLE_ID) as VEH_MAINTENANCE, " +
                    "        t.ICON_TYPE,         " +
                    "        d.profile_id as DEPT_ID " +
                    "    FROM "+schemaName+" nex_individual_temp t, "+schemaName+"NEX_EXTENDED_USER_VS_VEHICLE d      " +
                    "    where d.profile_id = "+userId+" " +
                    "    and d.profile_type = '2' " +
                    "    and d.parent_profile_id = "+parentProfileId+"      " +
                    "    and t.VEHICLE_ID = d.VEHICLE_ID  " +
                    "     AND ( " +
                    "            t.VEHICLE_ID = "+SPECIFIC_VEHICLE_ID+" " +
                    "            OR "+SPECIFIC_VEHICLE_ID+" IS NULL " +
                    "        )    " +
                    "    order by t.ID asc " +
                    " OFFSET "+offSet+" ROWS\n" +
                    "           FETCH NEXT "+limit+" ROWS ONLY)";
        } else if (userType==3) {
            sql="SELECT   " +
                    "    ROWNUM ROWNO,   " +
                    "    ID, VEHICLE_ID,  " +
                    "    ENGIN, LAT, LON, VDATE, VEH_MAINTENANCE,  " +
                    "    ICON_TYPE, "+schemaName+"GET_VEHICLE_NAME(0, VEHICLE_ID) as VEHICLE_NAME   " +
                    "FROM (  " +
                    "    SELECT   " +
                    "        ROWNUM ROWNO,  " +
                    "        t.ID, t.VEHICLE_ID,   " +
                    "        t.ENGIN, t.LAT, t.LON,   " +
                    "        TO_CHAR(TO_DATE(t.VDATE, 'YYYY-MM-DD HH24:MI:SS')-2 / 24, 'YYYY-MM-DD HH24:MI:SS') AS VDATE,  " +
                    "       "+schemaName+" GET_VEHICLE_SERVICE_STAT(t.VEHICLE_ID) AS VEH_MAINTENANCE,  " +
                    "        t.ICON_TYPE  " +
                    "    FROM "+schemaName+" NEX_INDIVIDUAL_TEMP t  " +
                    "    WHERE t.GROUP_ID =    " +parentProfileId+
                    "    and t.VEHICLE_ID =       " +SPECIFIC_VEHICLE_ID+
                    ")";

        }
        System.out.println("..........."+sql);

        Optional<ArrayList<VehicleStateInfoOra>> vehicleStateInfoList;

        try {

            vehicleStateInfoList = Optional.of((ArrayList<VehicleStateInfoOra>) jdbcTemplate.query(sql, new VehicleStateInfo_RowMapper(), new Object[]{}));

        } catch (BadSqlGrammarException e) {
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
