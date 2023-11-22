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

/*    public Integer total_vehicle_for_user_type_1(Integer groupId) {
        String total = "select count(a.ID) total from nex_individual_temp a, nex_individual_client b where a.VEHICLE_ID = b.ID and a.group_id = ? AND a.OPERATORID = 1 AND b.ACTIVATION = 1";
        Integer numberOfVehicles = jdbcTemplate.queryForObject(total, new Object[]{groupId}, new Total_Vehicle_RowMapper()).getNumberOfVehicles();
        return numberOfVehicles;
    }*/

/*    public Integer total_vehicle_for_user_type_2(Integer groupId, Integer parentId) {
        String total = "select count(a.ID) total from nex_individual_temp a, nex_individual_client b where a.VEHICLE_ID = b.id and a.VEHICLE_ID in  (select to_char(VEHICLE_ID) from GPSNEXGP.NEX_EXTENDED_USER_VS_VEHICLE WHERE PROFILE_ID = ?  AND PROFILE_TYPE = 2  AND PARENT_PROFILE_ID = ?) AND b.OPERATORID = 1 AND b.ACTIVATION = 1";
        Integer numberOfVehicles = jdbcTemplate.queryForObject(total, new Object[]{groupId, parentId}, new Total_Vehicle_RowMapper()).getNumberOfVehicles();
        return numberOfVehicles;
    }*/

/*    public Integer total_vehicle_for_user_type_3(Integer groupId) {
        String total = "select count(a.ID) total from nex_individual_temp a, nex_individual_client b where a.vehicle_id = b.id and a.VEHICLE_ID = ? AND a.OPERATORID = 1 AND b.ACTIVATION = 1";
        Integer numberOfVehicles = jdbcTemplate.queryForObject(total, new Object[]{groupId}, new Total_Vehicle_RowMapper()).getNumberOfVehicles();
        return numberOfVehicles;
    }*/

/*    public Integer total_vehicle_for_user_type_default(Integer groupId, Integer userType, Integer parentId) {
        String total = "select count(a.ID) total from nex_individual_temp a, nex_individual_client b where a.VEHICLE_ID = b.id and a.VEHICLE_ID in  (select to_char(VEHICLE_ID)   from GPSNEXGP.NEX_EXTENDED_USER_VS_VEHICLE  WHERE PROFILE_ID = ?  AND PROFILE_TYPE = ?  AND PARENT_PROFILE_ID = ?) AND b.OPERATORID = 1 AND b.ACTIVATION = 1";
        Integer numberOfVehicles = jdbcTemplate.queryForObject(total, new Object[]{groupId, userType, parentId}, new Total_Vehicle_RowMapper()).getNumberOfVehicles();
        return numberOfVehicles;
    }*/ /*todo -- groupId is equivalent to vehicleId,profileId as per php code & documentation*/

    @Override
    public Object getVehicleList(Integer id/*, String limit, Integer offset*/, Integer userType/*, Integer parentId*/, Integer operatorId,String vehicleID_MotherAccId) { /*todo --operatorId is constant and it is 1*/
/*        if (limit.equals("ALL")) {
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
            String getVehicleListQuery = "select a.VEHICLE_ID   id, a.USERID  vehicle_name, a.ENGIN  engine_status,  a.SPEED  speed,  b.FAVORITE   is_favorite,  b.ICON_TYPE   vehicle_icon_type, b.CUSTOM_USERID  user_defined_vehicle_name,  b.ICON_TYPE_ON_MAP  ICON_TYPE_ON_MAP,  b.ICON_TYPE_RUNNING   ICON_TYPE_RUNNING,  b.ICON_TYPE_STOPPED  ICON_TYPE_STOPPED, b.ICON_TYPE_STATIONARY  ICON_TYPE_STATIONARY,  GPSNEXGP.GET_MAX_CAR_SPEED(b.ID) max_speed from nex_individual_temp a,  nex_individual_client b where a.VEHICLE_ID = b.ID  and a.group_id = ? AND a.OPERATORID = ?  AND b.ACTIVATION = 1".concat("order by a.ORDER_INDEX, b.ID asc OFFSET ? ROWS FETCH FIRST ? ROWS ONLY");
            return jdbcTemplate.query(getVehicleListQuery, new Vehicle_List_RowMapper(), groupId, 1, offset, Integer.parseInt(limit));
        } else if (userType.equals(2)) {
            String getVehicleListQuery = "select a.VEHICLE_ID   id,  a.USERID  vehicle_name,  a.ENGIN  engine_status, a.SPEED  speed,  b.FAVORITE   is_favorite, b.ICON_TYPE  vehicle_icon_type,b.CUSTOM_USERID  user_defined_vehicle_name, b.ICON_TYPE_ON_MAP  ICON_TYPE_ON_MAP, b.ICON_TYPE_RUNNING ICON_TYPE_RUNNING, b.ICON_TYPE_STOPPED  ICON_TYPE_STOPPED, b.ICON_TYPE_STATIONARY  ICON_TYPE_STATIONARY, GPSNEXGP.GET_MAX_CAR_SPEED(b.ID) max_speed from nex_individual_temp a, nex_individual_client b where a.VEHICLE_ID = b.id and a.VEHICLE_ID in     (select to_char(VEHICLE_ID)   from GPSNEXGP.NEX_EXTENDED_USER_VS_VEHICLE  WHERE PROFILE_ID = ?  AND PROFILE_TYPE = 2  AND PARENT_PROFILE_ID = ?) AND b.OPERATORID = ? AND b.ACTIVATION = 1".concat("order by a.ORDER_INDEX, b.ID asc OFFSET ? ROWS FETCH FIRST ? ROWS ONLY");
            return jdbcTemplate.query(getVehicleListQuery, new Vehicle_List_RowMapper(), groupId, parentId, 1, offset, Integer.parseInt(limit));
        } else if (userType.equals(3)) {
            String getVehicleListQuery = "select a.VEHICLE_ID   id,  a.USERID  vehicle_name, a.ENGIN  engine_status,  a.SPEED  speed,b.FAVORITE  is_favorite,   b.ICON_TYPE  vehicle_icon_type,   b.CUSTOM_USERID   user_defined_vehicle_name,   b.ICON_TYPE_ON_MAP   ICON_TYPE_ON_MAP,   b.ICON_TYPE_RUNNING   ICON_TYPE_RUNNING,   b.ICON_TYPE_STOPPED   ICON_TYPE_STOPPED,  b.ICON_TYPE_STATIONARY  ICON_TYPE_STATIONARY,  GPSNEXGP.GET_MAX_CAR_SPEED(b.ID) max_speed from nex_individual_temp a,  nex_individual_client b where a.vehicle_id = b.id  and a.VEHICLE_ID = ?  AND a.OPERATORID = ?  AND b.ACTIVATION = 1".concat("order by a.ORDER_INDEX, b.ID asc OFFSET ? ROWS FETCH FIRST ? ROWS ONLY");
            return jdbcTemplate.query(getVehicleListQuery, new Vehicle_List_RowMapper(), groupId, 1, offset, Integer.parseInt(limit));
        } else {
            String getVehicleListQuery = "select a.VEHICLE_ID  id,a.USERID vehicle_name, a.ENGIN  engine_status,a.SPEED  speed,b.FAVORITE is_favorite,b.ICON_TYPE  vehicle_icon_type, b.CUSTOM_USERID user_defined_vehicle_name, b.ICON_TYPE_ON_MAP ICON_TYPE_ON_MAP,  b.ICON_TYPE_RUNNING  ICON_TYPE_RUNNING, b.ICON_TYPE_STOPPED ICON_TYPE_STOPPED, b.ICON_TYPE_STATIONARY ICON_TYPE_STATIONARY,GPSNEXGP.GET_MAX_CAR_SPEED(b.ID) max_speed from nex_individual_temp a, nex_individual_client b where a.VEHICLE_ID = b.id and a.VEHICLE_ID in (select to_char(VEHICLE_ID) from GPSNEXGP.NEX_EXTENDED_USER_VS_VEHICLE WHERE PROFILE_ID = ? AND PROFILE_TYPE = 6  AND PARENT_PROFILE_ID = ?) AND b.OPERATORID = ? AND b.ACTIVATION = 1".concat("order by a.ORDER_INDEX, b.ID asc OFFSET ? ROWS FETCH FIRST ? ROWS ONLY");
            return jdbcTemplate.query(getVehicleListQuery, new Vehicle_List_RowMapper(), groupId, parentId, 1, offset, Integer.parseInt(limit));
        }*/
        switch (userType) {
            case 1:
                try {
                    String query = "select ROWNUM ROWNO,ID,VEHICLE_ID,USERID,GROUP_ID,ENGIN,SPEED,LAT,LON,VDATE,FAVORITE,ICON_TYPE,ORDER_INDEX,DISTANCE,\n" +
                            "       get_vehicle_stat_his(VEHICLE_ID) as POSITION_HIS\n" +
                            "FROM (select t.ID,t.VEHICLE_ID,t.USERID,t.GROUP_ID,t.ENGIN,t.SPEED,t.LAT,t.LON,t.VDATE,t.FAVORITE,t.ICON_TYPE,t.ORDER_INDEX,'0' as DISTANCE\n" +
                            "      FROM nex_individual_temp t\n" +
                            "      where t.VEHICLE_ID IN (select ID\n" +
                            "                             from NEX_INDIVIDUAL_CLIENT\n" +
                            "                             where COMPANY_ID = ?\n" +
                            "                               and ACTIVATION = 1\n" +
                            "                               and OPERATORID =? )\n" +
                            "      order by t.USERID asc, t.ORDER_INDEX asc)";
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
                            "       b.CAR_MODEL             model,\n" +
                            "       GPSNEXGP.GET_MAX_CAR_SPEED(b.ID) max_speed\n" +
                            "from GPSNEXGP.nex_individual_temp a,\n" +
                            "     GPSNEXGP.nex_individual_client b\n" +
                            "where a.vehicle_id = b.id\n" +
                            "  and a.VEHICLE_ID = ?\n" +
                            "  AND b.OPERATORID = ?\n" +
                            "  AND b.ACTIVATION = 1";
                    return jdbcTemplate.query(query, new DetailsOfVehicle_RowMapper(),vehicleID_MotherAccId,operatorId) ;/*jdbcTemplate.queryForObject(query,new Object[]{vehicleID_MotherAccId,operatorId},(rs, rowNum) ->
                         new DetailsOfVehicle(
                                 rs.getString("id"),
                                 rs.getString("vehicle_name"),
                                 rs.getString("engine_status"),
                                 rs.getFloat("speed"),
                                 rs.getInt("is_favorite"),
                                 rs.getInt("vehicle_icon_type"),
                                 rs.getString("user_defined_vehicle_name"),
                                 rs.getInt("ICON_TYPE_ON_MAP"),
                                 rs.getInt("ICON_TYPE_RUNNING"),
                                 rs.getInt("ICON_TYPE_STOPPED"),
                                 rs.getInt("ICON_TYPE_STATIONARY"),
                                 rs.getString("registration_number"),
                                 rs.getString("color"),
                                 rs.getString("vendor"),
                                 rs.getString("model"),
                                 rs.getString("max_speed")
                         ));*/
                }
                catch (Exception e){
                    throw new AppCommonException(e.getMessage());
                }




        }
        return null;
    }
}
