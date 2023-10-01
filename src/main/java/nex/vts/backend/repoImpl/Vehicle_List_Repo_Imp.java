package nex.vts.backend.repoImpl;

import nex.vts.backend.models.vehicle.RowMapper.Total_Vehicle_RowMapper;
import nex.vts.backend.models.vehicle.Vehicle_List;
import nex.vts.backend.models.vehicle.rowMapper.Vehicle_List_RowMapper;
import nex.vts.backend.repositories.Vehicle_List_Repo;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@SuppressWarnings("all")
public class Vehicle_List_Repo_Imp implements Vehicle_List_Repo {
    private static JdbcTemplate jdbcTemplate;

    public Vehicle_List_Repo_Imp(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public  Object total_vehicle_for_user_type_1(Integer groupId) {
        String total = "select count(a.ID) total\n" + "from nex_individual_temp a,\n" + "     nex_individual_client b\n" + "where a.VEHICLE_ID = b.ID\n" + "  and a.group_id = ?\n" + "  AND a.OPERATORID = 1\n" + "  AND b.ACTIVATION = 1";
        return jdbcTemplate.query(total, new Total_Vehicle_RowMapper(), groupId);
    }
    public  Object total_vehicle_for_user_type_2(Integer groupId, Integer parentId) {
        String total = "select count(a.ID) total\n" + "from nex_individual_temp a,\n" + "     nex_individual_client b\n" + "where a.VEHICLE_ID = b.id\n" + "  and a.VEHICLE_ID in\n" + "      (select to_char(VEHICLE_ID)\n" + "       from GPSNEXGP.NEX_EXTENDED_USER_VS_VEHICLE\n" + "       WHERE PROFILE_ID = ?\n" + "         AND PROFILE_TYPE = 2\n" + "         AND PARENT_PROFILE_ID = ?)\n" + "  AND b.OPERATORID = 1\n" + "  AND b.ACTIVATION = 1";
        return jdbcTemplate.query(total, new Total_Vehicle_RowMapper(), groupId, parentId);
    }
    public  Object total_vehicle_for_user_type_3(Integer groupId) {
        String total = "select count(a.ID) total\n" + "from nex_individual_temp a,\n" + "     nex_individual_client b\n" + "where a.vehicle_id = b.id\n" + "  and a.VEHICLE_ID = ?\n" + "  AND a.OPERATORID = 1\n" + "  AND b.ACTIVATION = 1";
        return jdbcTemplate.query(total, new Total_Vehicle_RowMapper(), groupId);
    }
    public  Object total_vehicle_for_user_type_default(Integer groupId, Integer userType, Integer parentId) {
        String total = "select count(a.ID) total\n" + "from nex_individual_temp a,\n" + "     nex_individual_client b\n" + "where a.VEHICLE_ID = b.id\n" + "  and a.VEHICLE_ID in\n" + "      (select to_char(VEHICLE_ID)\n" + "       from GPSNEXGP.NEX_EXTENDED_USER_VS_VEHICLE\n" + "       WHERE PROFILE_ID = ?\n" + "         AND PROFILE_TYPE = ?\n" + "         AND PARENT_PROFILE_ID = ?)\n" + "  AND b.OPERATORID = 1\n" + "  AND b.ACTIVATION = 1";
        return jdbcTemplate.query(total, new Total_Vehicle_RowMapper(), groupId, userType, parentId);
    }
    @Override
    public List<Vehicle_List> getVehicleList(Integer groupId, Integer operationId, String limit, Integer offset, Integer userType, Integer parentId) {
        if (limit.equals("ALL")) {
            if (userType.equals(1)) {
                String getVehicleListQuery = "select a.VEHICLE_ID                     id,\n" + "       a.USERID                         vehicle_name,\n" + "       a.ENGIN                          engine_status,\n" + "       a.SPEED                          speed,\n" + "       b.FAVORITE                       is_favorite,\n" + "       b.ICON_TYPE                      vehicle_icon_type,\n" + "       b.CUSTOM_USERID                  user_defined_vehicle_name,\n" + "       b.ICON_TYPE_ON_MAP              ICON_TYPE_ON_MAP,\n" + "       b.ICON_TYPE_RUNNING             ICON_TYPE_RUNNING,\n" + "       b.ICON_TYPE_STOPPED              ICON_TYPE_STOPPED,\n" + "       b.ICON_TYPE_STATIONARY           ICON_TYPE_STATIONARY,\n" + "       GPSNEXGP.GET_MAX_CAR_SPEED(b.ID) max_speed\n" + "from nex_individual_temp a,\n" + "     nex_individual_client b\n" + "where a.VEHICLE_ID = b.ID\n" + "  and a.group_id = ?\n" + "  AND a.OPERATORID = ?\n" + "  AND b.ACTIVATION = 1";
//                respnse.put("total vehicles", total_vehicle_for_user_type_1(groupId));
//                respnse.put("vehicles", );
                return jdbcTemplate.query(getVehicleListQuery, new Vehicle_List_RowMapper(), groupId, operationId); /*Collections.singletonList(jdbcTemplate.query(getVehicleListQuery, new vehicleListRowMapper(), groupId, operationId));*/
            } else if (userType.equals(2)) {
                String getVehicleListQuery = "select a.VEHICLE_ID                     id,\n" + "       a.USERID                         vehicle_name,\n" + "       a.ENGIN                          engine_status,\n" + "       a.SPEED                          speed,\n" + "       b.FAVORITE                       is_favorite,\n" + "       b.ICON_TYPE                      vehicle_icon_type,\n" + "       b.CUSTOM_USERID                  user_defined_vehicle_name,\n" + "       b.ICON_TYPE_ON_MAP               ICON_TYPE_ON_MAP,\n" + "       b.ICON_TYPE_RUNNING              ICON_TYPE_RUNNING,\n" + "       b.ICON_TYPE_STOPPED              ICON_TYPE_STOPPED,\n" + "       b.ICON_TYPE_STATIONARY           ICON_TYPE_STATIONARY,\n" + "       GPSNEXGP.GET_MAX_CAR_SPEED(b.ID) max_speed\n" + "from nex_individual_temp a,\n" + "     nex_individual_client b\n" + "where a.VEHICLE_ID = b.id\n" + "  and a.VEHICLE_ID in\n" + "      (select to_char(VEHICLE_ID)\n" + "       from GPSNEXGP.NEX_EXTENDED_USER_VS_VEHICLE\n" + "       WHERE PROFILE_ID = ?\n" + "         AND PROFILE_TYPE = 2\n" + "         AND PARENT_PROFILE_ID = ?)\n" + "  AND b.OPERATORID = ?\n" + "  AND b.ACTIVATION = 1";
//                respnse.put("total vehicles", total_vehicle_for_user_type_2(groupId, parentId));
//                respnse.put("vehicles", );
                return jdbcTemplate.query(getVehicleListQuery, new Vehicle_List_RowMapper(), groupId, parentId, operationId);
            } else if (userType.equals(3)) {
                String getVehicleListQuery = "select a.VEHICLE_ID                     id,\n" + "       a.USERID                         vehicle_name,\n" + "       a.ENGIN                          engine_status,\n" + "       a.SPEED                          speed,\n" + "       b.FAVORITE                       is_favorite,\n" + "       b.ICON_TYPE                      vehicle_icon_type,\n" + "       b.CUSTOM_USERID                  user_defined_vehicle_name,\n" + "       b.ICON_TYPE_ON_MAP               ICON_TYPE_ON_MAP,\n" + "       b.ICON_TYPE_RUNNING              ICON_TYPE_RUNNING,\n" + "       b.ICON_TYPE_STOPPED              ICON_TYPE_STOPPED,\n" + "       b.ICON_TYPE_STATIONARY           ICON_TYPE_STATIONARY,\n" + "       GPSNEXGP.GET_MAX_CAR_SPEED(b.ID) max_speed\n" + "from nex_individual_temp a,\n" + "     nex_individual_client b\n" + "where a.vehicle_id = b.id\n" + "  and a.VEHICLE_ID = ?\n" + "  AND a.OPERATORID = ?\n" + "  AND b.ACTIVATION = 1";
//                respnse.put("total vehicles", total_vehicle_for_user_type_3(groupId));
//                respnse.put("vehicles", );
                return jdbcTemplate.query(getVehicleListQuery, new Vehicle_List_RowMapper(), groupId, operationId);
            } else {
                String getVehicleListQuery = "select a.VEHICLE_ID                     id,\n" + "       a.USERID                         vehicle_name,\n" + "       a.ENGIN                          engine_status,\n" + "       a.SPEED                          speed,\n" + "       b.FAVORITE                       is_favorite,\n" + "       b.ICON_TYPE                      vehicle_icon_type,\n" + "       b.CUSTOM_USERID                  user_defined_vehicle_name,\n" + "       b.ICON_TYPE_ON_MAP               ICON_TYPE_ON_MAP,\n" + "       b.ICON_TYPE_RUNNING              ICON_TYPE_RUNNING,\n" + "       b.ICON_TYPE_STOPPED              ICON_TYPE_STOPPED,\n" + "       b.ICON_TYPE_STATIONARY           ICON_TYPE_STATIONARY,\n" + "       GPSNEXGP.GET_MAX_CAR_SPEED(b.ID) max_speed\n" + "from nex_individual_temp a,\n" + "     nex_individual_client b\n" + "where a.VEHICLE_ID = b.id\n" + "  and a.VEHICLE_ID in\n" + "      (select to_char(VEHICLE_ID)\n" + "       from GPSNEXGP.NEX_EXTENDED_USER_VS_VEHICLE\n" + "       WHERE PROFILE_ID = ?\n" + "         AND PROFILE_TYPE = 6\n" + "         AND PARENT_PROFILE_ID = ?)\n" + "  AND b.OPERATORID = ?\n" + "  AND b.ACTIVATION = 1";
//                respnse.put("total vehicles", total_vehicle_for_user_type_default(groupId, userType, parentId));
//                respnse.put("vehicles", );
                return jdbcTemplate.query(getVehicleListQuery, new Vehicle_List_RowMapper(), groupId, parentId, operationId);
            }
        } else {
            Integer limits = Integer.parseInt(limit);
            if (userType.equals(1)) {
                String getVehicleListQuery = "select a.VEHICLE_ID                     id,\n" + "       a.USERID                         vehicle_name,\n" + "       a.ENGIN                          engine_status,\n" + "       a.SPEED                          speed,\n" + "       b.FAVORITE                       is_favorite,\n" + "       b.ICON_TYPE                      vehicle_icon_type,\n" + "       b.CUSTOM_USERID                  user_defined_vehicle_name,\n" + "       b.ICON_TYPE_ON_MAP              ICON_TYPE_ON_MAP,\n" + "       b.ICON_TYPE_RUNNING             ICON_TYPE_RUNNING,\n" + "       b.ICON_TYPE_STOPPED              ICON_TYPE_STOPPED,\n" + "       b.ICON_TYPE_STATIONARY           ICON_TYPE_STATIONARY,\n" + "       GPSNEXGP.GET_MAX_CAR_SPEED(b.ID) max_speed\n" + "from nex_individual_temp a,\n" + "     nex_individual_client b\n" + "where a.VEHICLE_ID = b.ID\n" + "  and a.group_id = ?\n" + "  AND a.OPERATORID = ?\n" + "  AND b.ACTIVATION = 1".concat("order by a.ORDER_INDEX, b.ID asc\n" + "OFFSET ? ROWS FETCH FIRST ? ROWS ONLY");
//                respnse.put("total vehicles", total_vehicle_for_user_type_1(groupId));
//                respnse.put("vehicles", );
                return jdbcTemplate.query(getVehicleListQuery, new Vehicle_List_RowMapper(), groupId, operationId, offset, limits);
            } else if (userType.equals(2)) {
                String getVehicleListQuery = "select a.VEHICLE_ID                     id,\n" + "       a.USERID                         vehicle_name,\n" + "       a.ENGIN                          engine_status,\n" + "       a.SPEED                          speed,\n" + "       b.FAVORITE                       is_favorite,\n" + "       b.ICON_TYPE                      vehicle_icon_type,\n" + "       b.CUSTOM_USERID                  user_defined_vehicle_name,\n" + "       b.ICON_TYPE_ON_MAP               ICON_TYPE_ON_MAP,\n" + "       b.ICON_TYPE_RUNNING              ICON_TYPE_RUNNING,\n" + "       b.ICON_TYPE_STOPPED              ICON_TYPE_STOPPED,\n" + "       b.ICON_TYPE_STATIONARY           ICON_TYPE_STATIONARY,\n" + "       GPSNEXGP.GET_MAX_CAR_SPEED(b.ID) max_speed\n" + "from nex_individual_temp a,\n" + "     nex_individual_client b\n" + "where a.VEHICLE_ID = b.id\n" + "  and a.VEHICLE_ID in\n" + "      (select to_char(VEHICLE_ID)\n" + "       from GPSNEXGP.NEX_EXTENDED_USER_VS_VEHICLE\n" + "       WHERE PROFILE_ID = ?\n" + "         AND PROFILE_TYPE = 2\n" + "         AND PARENT_PROFILE_ID = ?)\n" + "  AND b.OPERATORID = ?\n" + "  AND b.ACTIVATION = 1".concat("order by a.ORDER_INDEX, b.ID asc\n" + "OFFSET ? ROWS FETCH FIRST ? ROWS ONLY");
//                respnse.put("total vehicles", total_vehicle_for_user_type_2(groupId, parentId));
//                respnse.put("vehicles", );
                return jdbcTemplate.query(getVehicleListQuery, new Vehicle_List_RowMapper(), groupId, parentId, operationId, offset, limits);
            } else if (userType.equals(3)) {
                String getVehicleListQuery = "select a.VEHICLE_ID                     id,\n" + "       a.USERID                         vehicle_name,\n" + "       a.ENGIN                          engine_status,\n" + "       a.SPEED                          speed,\n" + "       b.FAVORITE                       is_favorite,\n" + "       b.ICON_TYPE                      vehicle_icon_type,\n" + "       b.CUSTOM_USERID                  user_defined_vehicle_name,\n" + "       b.ICON_TYPE_ON_MAP               ICON_TYPE_ON_MAP,\n" + "       b.ICON_TYPE_RUNNING              ICON_TYPE_RUNNING,\n" + "       b.ICON_TYPE_STOPPED              ICON_TYPE_STOPPED,\n" + "       b.ICON_TYPE_STATIONARY           ICON_TYPE_STATIONARY,\n" + "       GPSNEXGP.GET_MAX_CAR_SPEED(b.ID) max_speed\n" + "from nex_individual_temp a,\n" + "     nex_individual_client b\n" + "where a.vehicle_id = b.id\n" + "  and a.VEHICLE_ID = ?\n" + "  AND a.OPERATORID = ?\n" + "  AND b.ACTIVATION = 1".concat("order by a.ORDER_INDEX, b.ID asc\n" + "OFFSET ? ROWS FETCH FIRST ? ROWS ONLY");
//                respnse.put("total vehicles", total_vehicle_for_user_type_3(groupId));
//                respnse.put("vehicles", );
                return jdbcTemplate.query(getVehicleListQuery, new Vehicle_List_RowMapper(), groupId, operationId, offset, limits);
            } else {
                String getVehicleListQuery = "select a.VEHICLE_ID                     id,\n" + "       a.USERID                         vehicle_name,\n" + "       a.ENGIN                          engine_status,\n" + "       a.SPEED                          speed,\n" + "       b.FAVORITE                       is_favorite,\n" + "       b.ICON_TYPE                      vehicle_icon_type,\n" + "       b.CUSTOM_USERID                  user_defined_vehicle_name,\n" + "       b.ICON_TYPE_ON_MAP               ICON_TYPE_ON_MAP,\n" + "       b.ICON_TYPE_RUNNING              ICON_TYPE_RUNNING,\n" + "       b.ICON_TYPE_STOPPED              ICON_TYPE_STOPPED,\n" + "       b.ICON_TYPE_STATIONARY           ICON_TYPE_STATIONARY,\n" + "       GPSNEXGP.GET_MAX_CAR_SPEED(b.ID) max_speed\n" + "from nex_individual_temp a,\n" + "     nex_individual_client b\n" + "where a.VEHICLE_ID = b.id\n" + "  and a.VEHICLE_ID in\n" + "      (select to_char(VEHICLE_ID)\n" + "       from GPSNEXGP.NEX_EXTENDED_USER_VS_VEHICLE\n" + "       WHERE PROFILE_ID = ?\n" + "         AND PROFILE_TYPE = 6\n" + "         AND PARENT_PROFILE_ID = ?)\n" + "  AND b.OPERATORID = ?\n" + "  AND b.ACTIVATION = 1".concat("order by a.ORDER_INDEX, b.ID asc\n" + "OFFSET ? ROWS FETCH FIRST ? ROWS ONLY");
//                respnse.put("total vehicles", total_vehicle_for_user_type_default(groupId, userType, parentId));
//                respnse.put("vehicles", );
                return jdbcTemplate.query(getVehicleListQuery, new Vehicle_List_RowMapper(), groupId, parentId, operationId, offset, limits);
            }
        }
    }
}
