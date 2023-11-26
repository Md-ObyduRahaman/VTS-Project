package nex.vts.backend.repoImpl;

import nex.vts.backend.exceptions.AppCommonException;
import nex.vts.backend.models.responses.DetailsOfVehicle;
import nex.vts.backend.models.responses.DetailsOfVehicleItem;
import nex.vts.backend.models.vehicle.rowMapper.DetailsOfVehicle_RowMapper;
import nex.vts.backend.models.vehicle.rowMapper.Vehicle_List_RowMapper;
import nex.vts.backend.repositories.Vehicle_List_Repo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class Vehicle_List_Repo_Imp implements Vehicle_List_Repo {
    private static JdbcTemplate jdbcTemplate;
    private final short API_VERSION = 1;
    private Logger logger = LoggerFactory.getLogger(Vehicle_List_Repo_Imp.class);

    public Vehicle_List_Repo_Imp(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public Object getVehicleList(Integer id, Integer userType, Integer operatorId,String shcemaName) { /*todo --operatorId is constant and it is 1*/
        switch (userType) {
            case 1:
                try {
                    String query = "select ROWNUM ROWNO,ID,VEHICLE_ID,USERID,GROUP_ID,ENGIN,SPEED,LAT,LON,VDATE,FAVORITE,ICON_TYPE,ORDER_INDEX,DISTANCE,"
                            .concat(shcemaName).concat("get_vehicle_stat_his(VEHICLE_ID) as POSITION_HIS\n" +
                                    "                            FROM (select t.ID,t.VEHICLE_ID,t.USERID,t.GROUP_ID,t.ENGIN,t.SPEED,t.LAT,t.LON,t.VDATE,t.FAVORITE,t.ICON_TYPE,t.ORDER_INDEX,'0' as DISTANCE\n" +
                                    "                                  FROM nex_individual_temp t\n" +
                                    "                                  where t.VEHICLE_ID IN (select ID\n" +
                                    "                                                         from NEX_INDIVIDUAL_CLIENT\n" +
                                    "                                                         where COMPANY_ID = ?\n" +
                                    "                                                           and ACTIVATION = 1\n" +
                                    "                                                           and OPERATORID =?)\n" +
                                    "                                  order by t.USERID asc, t.ORDER_INDEX asc)");
                    return jdbcTemplate.query(query,new Vehicle_List_RowMapper(),id,operatorId);
                }catch (Exception e){
                    throw new AppCommonException(e.toString());/*when fail to execute query what will happen*/
                }
            case 3:
                try {
                    String query="select a.VEHICLE_ID            id,\n" +
                            "       a.USERID                vehicle_name,\n" +
                            "       a.ENGIN                 engine_status,\n" +
                            "       a.SPEED                 speed,\n" +
                            "       b.FAVORITE              is_favorite,\n" +
                            "       b.ICON_TYPE             vehicle_icon_type,\n" +
                            "       b.CUSTOM_USERID         user_defined_vehicle_name,\n" +
                            "       b.ICON_TYPE_ON_MAP,\n" +
                            "       b.ICON_TYPE_RUNNING,\n" +
                            "       b.ICON_TYPE_STOPPED,\n" +
                            "       b.ICON_TYPE_STATIONARY,\n" +
                            "       b.CAR_REG_NO            registration_number,\n" +
                            "       b.CAR_COLOUR            color,\n" +
                            "       b.CAR_VENDOR            vendor,\n" +
                            "       b.CAR_MODEL             model,".concat(shcemaName).concat("GET_MAX_CAR_SPEED(b.ID) max_speed\n" +
                                    "from ").concat(shcemaName).concat(" nex_individual_temp a,").concat(shcemaName).concat("nex_individual_client b\n" +
                                    "where a.vehicle_id = b.id\n" +
                                    "  and a.VEHICLE_ID = ?\n" +
                                    "  AND b.OPERATORID = ?\n" +
                                    "  AND b.ACTIVATION = 1");
                    return jdbcTemplate.query(query, new DetailsOfVehicle_RowMapper(),id,operatorId);
                }
                catch (Exception e){
                    throw new AppCommonException(e.getMessage());
                }
        }
        return null;
    }
}
