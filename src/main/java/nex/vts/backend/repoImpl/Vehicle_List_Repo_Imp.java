package nex.vts.backend.repoImpl;

import nex.vts.backend.models.responses.DeptAccVehicleList;
import nex.vts.backend.models.vehicle.rowMapper.IndivisualAccVehicleListRowMapper;
import nex.vts.backend.models.vehicle.rowMapper.MotherAccVehicleListRowMapper;
import nex.vts.backend.repositories.Vehicle_List_Repo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class Vehicle_List_Repo_Imp implements Vehicle_List_Repo {
    private static JdbcTemplate jdbcTemplate;
    private final short API_VERSION = 1;
    private Logger logger = LoggerFactory.getLogger(Vehicle_List_Repo_Imp.class);

    public Vehicle_List_Repo_Imp(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Object getVehicleList(Integer id, Integer userType, Integer operatorId, String shcemaName, Integer deptId) { /*todo --operatorId is constant and it is 1*/
        switch (userType) {
            case 1:
                try {
                    String query = "select ROWNUM ROWNO,ID,VEHICLE_ID,USERID,GROUP_ID,ENGIN,SPEED,LAT,LON,VDATE,FAVORITE,ICON_TYPE,ORDER_INDEX,DISTANCE,"
                            .concat(shcemaName).concat("get_vehicle_stat_his(VEHICLE_ID) as POSITION_HIS,\n" +
                                    "                                case\n" +
                                    "                                    when ENGIN = 'ON' AND SPEED > 0\n" +
                                    "                                    THEN 1\n" +
                                    "                                    WHEN ENGIN = 'ON' AND SPEED <= 0\n" +
                                    "                                    THEN 2\n" +
                                    "                                    WHEN ENGIN = 'OFF'\n" +
                                    "                                    THEN 3\n" +
                                    "                                    END iscolor")
                            .concat("                            FROM (select t.ID,t.VEHICLE_ID,t.USERID,t.GROUP_ID,t.ENGIN,t.SPEED,t.LAT,t.LON,t.VDATE,t.FAVORITE,t.ICON_TYPE,t.ORDER_INDEX,'0' as DISTANCE\n" +
                                    "                                  FROM nex_individual_temp t\n" +
                                    "                                  where t.VEHICLE_ID IN (select ID\n" +
                                    "                                                         from NEX_INDIVIDUAL_CLIENT\n" +
                                    "                                                         where COMPANY_ID = ?\n" +
                                    "                                                           and ACTIVATION = 1\n" +
                                    "                                                           and OPERATORID =?)\n" +
                                    "                                  order by t.USERID asc, t.ORDER_INDEX asc)\n" +
                                    "--                               order by ROWNO asc offset ? rows fetch first ? rows only");
                    return jdbcTemplate.query(query, new MotherAccVehicleListRowMapper(), id, operatorId);
                } catch (Exception e) {
                    return e.getMessage();/*when fail to execute query what will happen*/
                }
            case 2:
                try {
                    String query = "select a.VEHICLE_ID            id,\n" +
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
                            "       b.CAR_MODEL             model,"
                                    .concat(shcemaName).concat("GET_MAX_CAR_SPEED(b.ID) max_speed,\n" +
                                            "                                       case\n" +
                                            "                                    when a.ENGIN = 'ON' AND a.SPEED > 0\n" +
                                            "                                    THEN 1\n" +
                                            "                                    WHEN a.ENGIN = 'ON' AND a.SPEED <= 0\n" +
                                            "                                    THEN 2\n" +
                                            "                                    WHEN a.ENGIN = 'OFF'\n" +
                                            "                                    THEN 3\n" +
                                            "                                    END iscolor\n" +
                                            "from ").concat(shcemaName).concat("nex_individual_temp a,").concat(shcemaName).concat("nex_individual_client b\n" +
                                            "where a.VEHICLE_ID = b.id\n" +
                                            "  and a.VEHICLE_ID in\n" +
                                            "      (select to_char(VEHICLE_ID)\n" +
                                            "       from ").concat(shcemaName).concat("NEX_EXTENDED_USER_VS_VEHICLE\n" +
                                            "       WHERE PROFILE_ID = ?\n" +
                                            "         AND PROFILE_TYPE = 2\n" +
                                            "         AND PARENT_PROFILE_ID =?)\n" +
                                            "  AND b.OPERATORID = ?/*1*/\n" +
                                            "  AND b.ACTIVATION = 1"); /*todo somotimes need to off activation for checking data*/
                    return jdbcTemplate.query(query, new RowMapper<DeptAccVehicleList>() {
                        @Override
                        public DeptAccVehicleList mapRow(ResultSet rs, int rowNum) throws SQLException {
                            return new DeptAccVehicleList(
                                    rs.getInt("ICON_TYPE_STATIONARY"),
                                    rs.getInt("ICON_TYPE_ON_MAP"),
                                    rs.getString("vehicle_name"),
                                    rs.getInt("ICON_TYPE_STOPPED"),
                                    rs.getString("color"),
                                    rs.getString("user_defined_vehicle_name"),
                                    rs.getInt("max_speed"),
                                    rs.getFloat("speed"),
                                    rs.getString("engine_status"),
                                    rs.getString("registration_number"),
                                    rs.getString("vendor"),
                                    rs.getInt("vehicle_icon_type"),
                                    rs.getString("model"),
                                    rs.getString("id"),
                                    rs.getInt("is_favorite"),
                                    rs.getInt("ICON_TYPE_RUNNING"),
                                    rs.getInt("iscolor")
                            );
                        }
                    }, id, deptId, operatorId);

                } catch (Exception e) {
                    return e.getMessage();
                }

            case 3:
                try {
                    String query = "select a.VEHICLE_ID            id,\n" +
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
                            "       b.CAR_MODEL             model,"
                                    .concat(shcemaName).concat("GET_MAX_CAR_SPEED(b.ID) max_speed,\n" +
                                            "                                    case\n" +
                                            "                                    when a.ENGIN = 'ON' AND a.SPEED > 0\n" +
                                            "                                    THEN 1\n" +
                                            "                                    WHEN a.ENGIN = 'ON' AND a.SPEED <= 0\n" +
                                            "                                    THEN 2\n" +
                                            "                                    WHEN a.ENGIN = 'OFF'\n" +
                                            "                                    THEN 3\n" +
                                            "                                    END iscolor\n" +
                                            "from nex_individual_temp a,\n" +
                                            "     nex_individual_client b\n" +
                                            "where a.vehicle_id = b.id\n" +
                                            "  and a.VEHICLE_ID = ?\n" +
                                            "  AND b.OPERATORID = ?\n" +
                                            "  AND b.ACTIVATION = 1");
                    return jdbcTemplate.query(query, new IndivisualAccVehicleListRowMapper(), id, operatorId);
                } catch (Exception e) {
                    return e.getMessage();
                }
            default:
                return "vehicle list is empty";
        }
    }

}
