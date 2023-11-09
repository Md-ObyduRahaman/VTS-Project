package nex.vts.backend.repoImpl;

import nex.vts.backend.models.responses.VehiclesItem;
import nex.vts.backend.models.vehicle.rowMapper.Total_Vehicle_RowMapper;
import nex.vts.backend.models.vehicle.rowMapper.Vehicle_List_RowMapper;
import nex.vts.backend.repositories.Vehicle_List_Repo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class Vehicle_List_Repo_Imp implements Vehicle_List_Repo {
    private static JdbcTemplate jdbcTemplate;
    private final short API_VERSION = 1;
    private Logger logger = LoggerFactory.getLogger(Vehicle_List_Repo_Imp.class);

    public Vehicle_List_Repo_Imp(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Integer total_vehicle_for_user_type_1(Integer groupId) {
        String total = "select count(a.ID) total from nex_individual_temp a, nex_individual_client b where a.VEHICLE_ID = b.ID and a.group_id = ? AND a.OPERATORID = 1 AND b.ACTIVATION = 1";
        Integer numberOfVehicles = jdbcTemplate.queryForObject(total, new Object[]{groupId}, new Total_Vehicle_RowMapper()).getNumberOfVehicles();
        return numberOfVehicles;
    }

    public Integer total_vehicle_for_user_type_2(Integer groupId, Integer parentId) {
        String total = "select count(a.ID) total from nex_individual_temp a, nex_individual_client b where a.VEHICLE_ID = b.id and a.VEHICLE_ID in  (select to_char(VEHICLE_ID) from GPSNEXGP.NEX_EXTENDED_USER_VS_VEHICLE WHERE PROFILE_ID = ?  AND PROFILE_TYPE = 2  AND PARENT_PROFILE_ID = ?) AND b.OPERATORID = 1 AND b.ACTIVATION = 1";
        Integer numberOfVehicles = jdbcTemplate.queryForObject(total, new Object[]{groupId, parentId}, new Total_Vehicle_RowMapper()).getNumberOfVehicles();
        return numberOfVehicles;
    }

    public Integer total_vehicle_for_user_type_3(Integer groupId) {
        String total = "select count(a.ID) total from nex_individual_temp a, nex_individual_client b where a.vehicle_id = b.id and a.VEHICLE_ID = ? AND a.OPERATORID = 1 AND b.ACTIVATION = 1";
        Integer numberOfVehicles = jdbcTemplate.queryForObject(total, new Object[]{groupId}, new Total_Vehicle_RowMapper()).getNumberOfVehicles();
        return numberOfVehicles;
    }

    public Integer total_vehicle_for_user_type_default(Integer groupId, Integer userType, Integer parentId) {
        String total = "select count(a.ID) total from nex_individual_temp a, nex_individual_client b where a.VEHICLE_ID = b.id and a.VEHICLE_ID in  (select to_char(VEHICLE_ID)   from GPSNEXGP.NEX_EXTENDED_USER_VS_VEHICLE  WHERE PROFILE_ID = ?  AND PROFILE_TYPE = ?  AND PARENT_PROFILE_ID = ?) AND b.OPERATORID = 1 AND b.ACTIVATION = 1";
        Integer numberOfVehicles = jdbcTemplate.queryForObject(total, new Object[]{groupId, userType, parentId}, new Total_Vehicle_RowMapper()).getNumberOfVehicles();
        return numberOfVehicles;
    } /*todo -- groupId is equivalent to vehicleId,profileId as per php code & documentation*/

    @Override
    public List<VehiclesItem> getVehicleList(Integer groupId, String limit, Integer offset, Integer userType, Integer parentId) { /*todo --operatorId is constant and it is 1*/
        System.out.println("Start method");
        System.out.println(limit);
        System.out.println(limit + " " + groupId + " " + userType + " " + parentId + " " + offset); /*        String limits = limit.substring(1,limit.length()-1); */
        if (limit.equals("ALL")) {
            System.out.println("Debug 2");
            if (userType.equals(1)) {
                String getVehicleListQuery = "select a.VEHICLE_ID id, a.USERID vehicle_name, a.ENGIN engine_status, a.SPEED speed, b.FAVORITE is_favorite, b.ICON_TYPE vehicle_icon_type, b.CUSTOM_USERID user_defined_vehicle_name, b.ICON_TYPE_ON_MAP ICON_TYPE_ON_MAP, b.ICON_TYPE_RUNNING ICON_TYPE_RUNNING, b.ICON_TYPE_STOPPED ICON_TYPE_STOPPED, b.ICON_TYPE_STATIONARY ICON_TYPE_STATIONARY, GPSNEXGP.GET_MAX_CAR_SPEED(b.ID) max_speed from nex_individual_temp a, nex_individual_client b where a.VEHICLE_ID = b.ID and a.group_id = ? AND a.OPERATORID = ? AND b.ACTIVATION = 1";
                return jdbcTemplate.query(getVehicleListQuery, new Vehicle_List_RowMapper(), groupId, 1);
            } else if (userType.equals(2)) {
                String getVehicleListQuery = "select a.VEHICLE_ID  id, a.USERID vehicle_name,a.ENGIN engine_status,a.SPEED speed,b.FAVORITE  is_favorite, b.ICON_TYPE vehicle_icon_type, b.CUSTOM_USERID  user_defined_vehicle_name, b.ICON_TYPE_ON_MAP ICON_TYPE_ON_MAP,b.ICON_TYPE_RUNNING ICON_TYPE_RUNNING,b.ICON_TYPE_STOPPED  ICON_TYPE_STOPPED, b.ICON_TYPE_STATIONARY ICON_TYPE_STATIONARY, GPSNEXGP.GET_MAX_CAR_SPEED(b.ID) max_speed from nex_individual_temp a, nex_individual_client b where a.VEHICLE_ID = b.id  and a.VEHICLE_ID in  (select to_char(VEHICLE_ID)  from GPSNEXGP.NEX_EXTENDED_USER_VS_VEHICLE WHERE PROFILE_ID = ? AND PROFILE_TYPE = 2 AND PARENT_PROFILE_ID = ?) AND b.OPERATORID = ? AND b.ACTIVATION = 1";
                return jdbcTemplate.query(getVehicleListQuery, new Vehicle_List_RowMapper(), groupId, parentId, 1);
            } else if (userType.equals(3)) {
                String getVehicleListQuery = "select a.VEHICLE_ID   id,   a.USERID  vehicle_name,      a.ENGIN  engine_status, a.SPEED  speed,  b.FAVORITE  is_favorite,      b.ICON_TYPE  vehicle_icon_type,  b.CUSTOM_USERID   user_defined_vehicle_name, b.ICON_TYPE_ON_MAP  ICON_TYPE_ON_MAP,       b.ICON_TYPE_RUNNING   ICON_TYPE_RUNNING,       b.ICON_TYPE_STOPPED  ICON_TYPE_STOPPED,   b.ICON_TYPE_STATIONARY  ICON_TYPE_STATIONARY,      GPSNEXGP.GET_MAX_CAR_SPEED(b.ID) max_speed from nex_individual_temp a,    nex_individual_client b where a.vehicle_id = b.id and a.VEHICLE_ID = ? AND a.OPERATORID = ?  AND b.ACTIVATION = 1";
                return jdbcTemplate.query(getVehicleListQuery, new Vehicle_List_RowMapper(), groupId, 1);
            } else {
                String getVehicleListQuery = "select a.VEHICLE_ID   id,       a.USERID vehicle_name,       a.ENGIN   engine_status,  a.SPEED   speed,      b.FAVORITE  is_favorite,       b.ICON_TYPE  vehicle_icon_type,      b.CUSTOM_USERID   user_defined_vehicle_name,       b.ICON_TYPE_ON_MAP   ICON_TYPE_ON_MAP, b.ICON_TYPE_RUNNING   ICON_TYPE_RUNNING,      b.ICON_TYPE_STOPPED   ICON_TYPE_STOPPED,      b.ICON_TYPE_STATIONARY           ICON_TYPE_STATIONARY,      GPSNEXGP.GET_MAX_CAR_SPEED(b.ID) max_speed from nex_individual_temp a,   nex_individual_client b where a.VEHICLE_ID = b.id and a.VEHICLE_ID in      (select to_char(VEHICLE_ID)       from GPSNEXGP.NEX_EXTENDED_USER_VS_VEHICLE       WHERE PROFILE_ID = ?        AND PROFILE_TYPE = 6        AND PARENT_PROFILE_ID = ?)  AND b.OPERATORID = ?  AND b.ACTIVATION = 1";
                return jdbcTemplate.query(getVehicleListQuery, new Vehicle_List_RowMapper(), groupId, parentId, 1);
            }
        } else if (userType == 1) {
            System.out.println("debug 3");
            String getVehicleListQuery = "select a.VEHICLE_ID   id, a.USERID  vehicle_name, a.ENGIN  engine_status,  a.SPEED  speed,  b.FAVORITE   is_favorite,  b.ICON_TYPE   vehicle_icon_type, b.CUSTOM_USERID  user_defined_vehicle_name,  b.ICON_TYPE_ON_MAP  ICON_TYPE_ON_MAP,  b.ICON_TYPE_RUNNING   ICON_TYPE_RUNNING,  b.ICON_TYPE_STOPPED  ICON_TYPE_STOPPED, b.ICON_TYPE_STATIONARY  ICON_TYPE_STATIONARY,  GPSNEXGP.GET_MAX_CAR_SPEED(b.ID) max_speed from nex_individual_temp a,  nex_individual_client b where a.VEHICLE_ID = b.ID  and a.group_id = ? AND a.OPERATORID = ?  AND b.ACTIVATION = 1".concat("order by a.ORDER_INDEX, b.ID asc OFFSET ? ROWS FETCH FIRST ? ROWS ONLY");
            System.out.println(getVehicleListQuery);
            return jdbcTemplate.query(getVehicleListQuery, new Vehicle_List_RowMapper(), groupId, 1, offset, limit);
        } else if (userType.equals(2)) {
            System.out.println("Debug 5");
            String getVehicleListQuery = "select a.VEHICLE_ID   id,  a.USERID  vehicle_name,  a.ENGIN  engine_status, a.SPEED  speed,  b.FAVORITE   is_favorite, b.ICON_TYPE  vehicle_icon_type,b.CUSTOM_USERID  user_defined_vehicle_name, b.ICON_TYPE_ON_MAP  ICON_TYPE_ON_MAP, b.ICON_TYPE_RUNNING ICON_TYPE_RUNNING, b.ICON_TYPE_STOPPED  ICON_TYPE_STOPPED, b.ICON_TYPE_STATIONARY  ICON_TYPE_STATIONARY, GPSNEXGP.GET_MAX_CAR_SPEED(b.ID) max_speed from nex_individual_temp a, nex_individual_client b where a.VEHICLE_ID = b.id and a.VEHICLE_ID in     (select to_char(VEHICLE_ID)   from GPSNEXGP.NEX_EXTENDED_USER_VS_VEHICLE  WHERE PROFILE_ID = ?  AND PROFILE_TYPE = 2  AND PARENT_PROFILE_ID = ?) AND b.OPERATORID = ? AND b.ACTIVATION = 1".concat("order by a.ORDER_INDEX, b.ID asc OFFSET ? ROWS FETCH FIRST ? ROWS ONLY");
            System.out.println(getVehicleListQuery);
            return jdbcTemplate.query(getVehicleListQuery, new Vehicle_List_RowMapper(), groupId, parentId, 1, offset, Integer.parseInt(limit));
        } else if (userType.equals(3)) {
            System.out.println("Debug 6");
            String getVehicleListQuery = "select a.VEHICLE_ID   id,  a.USERID  vehicle_name, a.ENGIN  engine_status,  a.SPEED  speed,b.FAVORITE  is_favorite,   b.ICON_TYPE  vehicle_icon_type,   b.CUSTOM_USERID   user_defined_vehicle_name,   b.ICON_TYPE_ON_MAP   ICON_TYPE_ON_MAP,   b.ICON_TYPE_RUNNING   ICON_TYPE_RUNNING,   b.ICON_TYPE_STOPPED   ICON_TYPE_STOPPED,  b.ICON_TYPE_STATIONARY  ICON_TYPE_STATIONARY,  GPSNEXGP.GET_MAX_CAR_SPEED(b.ID) max_speed from nex_individual_temp a,  nex_individual_client b where a.vehicle_id = b.id  and a.VEHICLE_ID = ?  AND a.OPERATORID = ?  AND b.ACTIVATION = 1".concat("order by a.ORDER_INDEX, b.ID asc OFFSET ? ROWS FETCH FIRST ? ROWS ONLY");
            System.out.println(getVehicleListQuery);
            return jdbcTemplate.query(getVehicleListQuery, new Vehicle_List_RowMapper(), groupId, 1, offset, Integer.parseInt(limit));
        } else {
            System.out.println("debug 4");
            String getVehicleListQuery = "select a.VEHICLE_ID  id,a.USERID vehicle_name, a.ENGIN  engine_status,a.SPEED  speed,b.FAVORITE is_favorite,b.ICON_TYPE  vehicle_icon_type, b.CUSTOM_USERID user_defined_vehicle_name, b.ICON_TYPE_ON_MAP ICON_TYPE_ON_MAP,  b.ICON_TYPE_RUNNING  ICON_TYPE_RUNNING, b.ICON_TYPE_STOPPED ICON_TYPE_STOPPED, b.ICON_TYPE_STATIONARY ICON_TYPE_STATIONARY,GPSNEXGP.GET_MAX_CAR_SPEED(b.ID) max_speed from nex_individual_temp a, nex_individual_client b where a.VEHICLE_ID = b.id and a.VEHICLE_ID in (select to_char(VEHICLE_ID) from GPSNEXGP.NEX_EXTENDED_USER_VS_VEHICLE WHERE PROFILE_ID = ? AND PROFILE_TYPE = 6  AND PARENT_PROFILE_ID = ?) AND b.OPERATORID = ? AND b.ACTIVATION = 1".concat("order by a.ORDER_INDEX, b.ID asc OFFSET ? ROWS FETCH FIRST ? ROWS ONLY");
            System.out.println(getVehicleListQuery);
            return jdbcTemplate.query(getVehicleListQuery, new Vehicle_List_RowMapper(), groupId, parentId, 1, offset, Integer.parseInt(limit));
        }
    }
}
