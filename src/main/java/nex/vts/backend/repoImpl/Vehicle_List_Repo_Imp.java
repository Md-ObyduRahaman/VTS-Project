package nex.vts.backend.repoImpl;

import nex.vts.backend.exceptions.AppCommonException;
import nex.vts.backend.models.responses.VehicleInfos;
import nex.vts.backend.repositories.Vehicle_List_Repo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Repository
public class Vehicle_List_Repo_Imp implements Vehicle_List_Repo {
    private static JdbcTemplate jdbcTemplate;
    private final short API_VERSION = 1;
    private Environment environment;
    private Logger logger = LoggerFactory.getLogger(Vehicle_List_Repo_Imp.class);

    String getQuery = null;

    @Autowired
    public Vehicle_List_Repo_Imp(JdbcTemplate jdbcTemplate,Environment environment) {
        this.jdbcTemplate = jdbcTemplate;
        this.environment = environment;
    }

    @Override
    public Object getVehicleList(Integer id, Integer userType, Integer operatorId, String shcemaName, Integer deptId) { /*todo --operatorId is constant and it is 1*/

        switch (userType) {

            case 1: /*TODO GP,M2M Mother Account*/
/*                try {
                    String query = "select ROWNUM ROWNO,ID,VEHICLE_ID,USERID,GROUP_ID,ENGIN,SPEED,LAT,LON,VDATE,FAVORITE,ICON_TYPE,ORDER_INDEX,DISTANCE,"
                            .concat(shcemaName).concat("get_vehicle_stat_his(VEHICLE_ID) as POSITION_HIS," +
                                    "                                case" +
                                    "                                    when ENGIN = 'ON' AND SPEED > 0" +
                                    "                                    THEN 1" +
                                    "                                    WHEN ENGIN = 'ON' AND SPEED <= 0" +
                                    "                                    THEN 2" +
                                    "                                    WHEN ENGIN = 'OFF'" +
                                    "                                    THEN 3" +
                                    "                                    END iscolor")
                            .concat("                            FROM (select t.ID,t.VEHICLE_ID,t.USERID,t.GROUP_ID,t.ENGIN,t.SPEED,t.LAT,t.LON,t.VDATE,t.FAVORITE,t.ICON_TYPE,t.ORDER_INDEX,'0' as DISTANCE" +
                                    "                                  FROM nex_individual_temp t" +
                                    "                                  where t.VEHICLE_ID IN (select ID" +
                                    "                                                         from NEX_INDIVIDUAL_CLIENT" +
                                    "                                                         where COMPANY_ID = ?" +
                                    "                                                           and ACTIVATION = 1" +
                                    "                                                           and OPERATORID =?)" +
                                    "                                  order by t.USERID asc, t.ORDER_INDEX asc)" +
                                    "--                               order by ROWNO asc offset ? rows fetch first ? rows only");
                    return jdbcTemplate.query(query, new MotherAccVehicleListRowMapper(), id, operatorId);
                } catch (Exception e) {

                    logger.error(e.getCause().getMessage());
                    *//*throw new AppCommonException(e.getMessage());*//*
                }*/
                getQuery = "select t.ID          ID," +
                        "       t.VEHICLE_ID  VEHICLEID," +
                        "       t.ENGIN       ENGINE," +
                        "       t.SPEED       SPEED," +
                        "       t.LAT         LAT," +
                        "       t.LON         LON," +
                        "       t.FAVORITE    FAVORITE," +
                        "       t.ICON_TYPE   ICONTYPE," +
                        "       t.ORDER_INDEX ORDERINDEX," +
                        "       t.USERID      USERID"+
                        "FROM nex_individual_temp t" +
                        "where t.VEHICLE_ID IN" +
                        "      (select ID" +
                        "       from NEX_INDIVIDUAL_CLIENT" +
                        "       where COMPANY_ID = ?" +
                        "         and ACTIVATION = 1" +
                        "         and OPERATORID = ?)" +
                        "order by t.USERID asc, t.ORDER_INDEX asc";
                try {

                    return jdbcTemplate.query(getQuery, new RowMapper<VehicleInfos>() {
                        @Override
                        public VehicleInfos mapRow(ResultSet rs, int rowNum) throws SQLException {
                            return new VehicleInfos(

                                    rs.getInt("ID"),
                                    rs.getString("VEHICLEID"),
                                    rs.getString("ENGINE"),
                                    rs.getFloat("SPEED"),
                                    rs.getFloat("LAT"),
                                    rs.getFloat("LON"),
                                    rs.getInt("FAVORITE"),
                                    rs.getInt("ICONTYPE"),
                                    rs.getInt("ORDERINDEX"),
                                    rs.getString("USERID")

                            );
                        }
                    }, id, operatorId);
                }catch (Exception e){

                    logger.error(e.getMessage());
                    throw new AppCommonException(606 + "##you have not permission to see the vehicle list" + id + "##" + API_VERSION);
                }
            case 2:/*TODO Department/Child Account*/
//                try {
//                    String query = "select a.VEHICLE_ID            id," +
//                            "       a.USERID                vehicle_name," +
//                            "       a.ENGIN                 engine_status," +
//                            "       a.SPEED                 speed," +
//                            "       b.FAVORITE              is_favorite," +
//                            "       b.ICON_TYPE             vehicle_icon_type," +
//                            "       b.CUSTOM_USERID         user_defined_vehicle_name," +
//                            "       b.ICON_TYPE_ON_MAP," +
//                            "       b.ICON_TYPE_RUNNING," +
//                            "       b.ICON_TYPE_STOPPED," +
//                            "       b.ICON_TYPE_STATIONARY," +
//                            "       b.CAR_REG_NO            registration_number," +
//                            "       b.CAR_COLOUR            color," +
//                            "       b.CAR_VENDOR            vendor," +
//                            "       b.CAR_MODEL             model,"
//                                    .concat(shcemaName).concat("GET_MAX_CAR_SPEED(b.ID) max_speed," +
//                                            "                                       case" +
//                                            "                                    when a.ENGIN = 'ON' AND a.SPEED > 0" +
//                                            "                                    THEN 1" +
//                                            "                                    WHEN a.ENGIN = 'ON' AND a.SPEED <= 0" +
//                                            "                                    THEN 2" +
//                                            "                                    WHEN a.ENGIN = 'OFF'" +
//                                            "                                    THEN 3" +
//                                            "                                    END iscolor" +
//                                            "from ").concat(shcemaName).concat("nex_individual_temp a,").concat(shcemaName).concat("nex_individual_client b" +
//                                            "where a.VEHICLE_ID = b.id" +
//                                            "  and a.VEHICLE_ID in" +
//                                            "      (select to_char(VEHICLE_ID)" +
//                                            "       from ").concat(shcemaName).concat("NEX_EXTENDED_USER_VS_VEHICLE" +
//                                            "       WHERE PROFILE_ID = ?" +
//                                            "         AND PROFILE_TYPE = 2" +
//                                            "         AND PARENT_PROFILE_ID =?)" +
//                                            "  AND b.OPERATORID = ?/*1*/" +
//                                            "  AND b.ACTIVATION = 1"); /*todo somotimes need to off activation for checking data*/
//                    return jdbcTemplate.query(query, new RowMapper<DeptAccVehicleList>() {
//                        @Override
//                        public DeptAccVehicleList mapRow(ResultSet rs, int rowNum) throws SQLException {
//                            return new DeptAccVehicleList(
//                                    rs.getInt("ICON_TYPE_STATIONARY"),
//                                    rs.getInt("ICON_TYPE_ON_MAP"),
//                                    rs.getString("vehicle_name"),
//                                    rs.getInt("ICON_TYPE_STOPPED"),
//                                    rs.getString("color"),
//                                    rs.getString("user_defined_vehicle_name"),
//                                    rs.getInt("max_speed"),
//                                    rs.getFloat("speed"),
//                                    rs.getString("engine_status"),
//                                    rs.getString("registration_number"),
//                                    rs.getString("vendor"),
//                                    rs.getInt("vehicle_icon_type"),
//                                    rs.getString("model"),
//                                    rs.getString("id"),
//                                    rs.getInt("is_favorite"),
//                                    rs.getInt("ICON_TYPE_RUNNING"),
//                                    rs.getInt("iscolor")
//                            );
//                        }
//                    }, id, deptId, operatorId);
//
//                } catch (Exception e) {
//
//                    logger.error(e.getCause().getMessage());
//                    /*throw new AppCommonException(e.getMessage());*/
//                }
                String activeOparator = environment.getProperty("spring.profiles.active");

                switch (activeOparator){

                    case "m2m":

                        getQuery = "select t.ID          ID," +
                                "       t.VEHICLE_ID  VEHICLEID," +
                                "       t.ENGIN       ENGINE," +
                                "       t.SPEED       SPEED," +
                                "       t.LAT         LAT," +
                                "       t.LON         LON," +
                                "       t.FAVORITE    FAVORITE," +
                                "       t.ICON_TYPE   ICONTYPE," +
                                "       t.ORDER_INDEX ORDERINDEX," +
                                "       t.USERID      USERID"+
                                "FROM nex_individual_temp t," +
                                "     nex_dept_wise_vehicle d" +
                                "where d.COMPANY_ID = ?" +
                                "  and d.DEPT_ID = ?" +
                                "  and d.ACTIVATION = 1" +
                                "  and d.OPERATORID = ?" +
                                "  and t.GROUP_ID = d.COMPANY_ID" +
                                "  and t.VEHICLE_ID = d.VEHICLE_ID" +
                                "order by t.USERID asc, t.ORDER_INDEX asc";
                        try
                        {
                        return jdbcTemplate.query(getQuery, new RowMapper<VehicleInfos>() {
                            @Override
                            public VehicleInfos mapRow(ResultSet rs, int rowNum) throws SQLException {

                                return new VehicleInfos(

                                        rs.getInt("ID"),
                                        rs.getString("VEHICLEID"),
                                        rs.getString("ENGINE"),
                                        rs.getFloat("SPEED"),
                                        rs.getFloat("LAT"),
                                        rs.getFloat("LON"),
                                        rs.getInt("FAVORITE"),
                                        rs.getInt("ICONTYPE"),
                                        rs.getInt("ORDERINDEX"),
                                        rs.getString("USERID")
                                );
                            }
                        },deptId,id,operatorId);

                        }catch (Exception e){

                            logger.error(e.getMessage());
                            throw new AppCommonException(700 + "##Did not provide required field" + "##" + API_VERSION);
                        }

                    case "gp":

                        getQuery = "select t.ID          ID," +
                                "       t.VEHICLE_ID  VEHICLEID," +
                                "       t.ENGIN       ENGINE," +
                                "       t.SPEED       SPEED," +
                                "       t.LAT         LAT," +
                                "       t.LON         LON," +
                                "       t.FAVORITE    FAVORITE," +
                                "       t.ICON_TYPE   ICONTYPE," +
                                "       t.ORDER_INDEX ORDERINDEX," +
                                "       t.USERID      USERID" +
                                "FROM nex_individual_temp t," +
                                "     GPSNEXGP.nex_dept_wise_vehicle d" +
                                "where d.COMPANY_ID = ?" +
                                "  and d.DEPT_ID = ?" +
                                "  and d.ACTIVATION = 1" +
                                " -- and d.OPERATORID = 1" +
                                "  and t.GROUP_ID = d.COMPANY_ID" +
                                "  and t.VEHICLE_ID = d.VEHICLE_ID" +
                                "order by t.USERID asc, t.ORDER_INDEX asc";
                        try{
                            return jdbcTemplate.query(getQuery, new RowMapper<VehicleInfos>() {
                                @Override
                                public VehicleInfos mapRow(ResultSet rs, int rowNum) throws SQLException {

                                    return new VehicleInfos(

                                            rs.getInt("ID"),
                                            rs.getString("VEHICLEID"),
                                            rs.getString("ENGINE"),
                                            rs.getFloat("SPEED"),
                                            rs.getFloat("LAT"),
                                            rs.getFloat("LON"),
                                            rs.getInt("FAVORITE"),
                                            rs.getInt("ICONTYPE"),
                                            rs.getInt("ORDERINDEX"),
                                            rs.getString("USERID")
                                    );
                                }
                            },deptId,id);
                        }
                        catch (Exception e){

                            logger.error(e.getMessage());
                            throw new AppCommonException(700 + "##Did not provide required field" + "##" + API_VERSION);
                        }


                }
            case 3:/*TODO Indivisual Account*/

                getQuery = "select t.ID          ID," +
                        "       t.VEHICLE_ID  VEHICLEID," +
                        "       t.ENGIN       ENGINE," +
                        "       t.SPEED       SPEED," +
                        "       t.LAT         LAT," +
                        "       t.LON         LON," +
                        "       t.FAVORITE    FAVORITE," +
                        "       t.ICON_TYPE   ICONTYPE," +
                        "       t.ORDER_INDEX ORDERINDEX," +
                        "       t.USERID      USERID" +
                        "from nex_individual_temp t," +
                        "     nex_individual_client b" +
                        "where t.vehicle_id = b.id" +
                        "  and t.VEHICLE_ID = ?" +
                        "  AND b.OPERATORID = ?" +
                        "  AND b.ACTIVATION = 1";

                try {
                    return jdbcTemplate.query(getQuery, new RowMapper<VehicleInfos>() {
                        @Override
                        public VehicleInfos mapRow(ResultSet rs, int rowNum) throws SQLException {

                            return new VehicleInfos(

                                    rs.getInt("ID"),
                                    rs.getString("VEHICLEID"),
                                    rs.getString("ENGINE"),
                                    rs.getFloat("SPEED"),
                                    rs.getFloat("LAT"),
                                    rs.getFloat("LON"),
                                    rs.getInt("FAVORITE"),
                                    rs.getInt("ICONTYPE"),
                                    rs.getInt("ORDERINDEX"),
                                    rs.getString("USERID")

                            );
                        }
                    },id,operatorId);
                }
                catch (Exception e){

                    logger.error(e.getMessage());
                    throw new AppCommonException(700 + "##Did not provide required field" + "##" + API_VERSION);
                }

            default:
                return null;
//            case 3:
//                try {
//                    String query = "select a.VEHICLE_ID            id," +
//                            "       a.USERID                vehicle_name," +
//                            "       a.ENGIN                 engine_status," +
//                            "       a.SPEED                 speed," +
//                            "       b.FAVORITE              is_favorite," +
//                            "       b.ICON_TYPE             vehicle_icon_type," +
//                            "       b.CUSTOM_USERID         user_defined_vehicle_name," +
//                            "       b.ICON_TYPE_ON_MAP," +
//                            "       b.ICON_TYPE_RUNNING," +
//                            "       b.ICON_TYPE_STOPPED," +
//                            "       b.ICON_TYPE_STATIONARY," +
//                            "       b.CAR_REG_NO            registration_number," +
//                            "       b.CAR_COLOUR            color," +
//                            "       b.CAR_VENDOR            vendor," +
//                            "       b.CAR_MODEL             model,"
//                                    .concat(shcemaName).concat("GET_MAX_CAR_SPEED(b.ID) max_speed," +
//                                            "                                    case" +
//                                            "                                    when a.ENGIN = 'ON' AND a.SPEED > 0" +
//                                            "                                    THEN 1" +
//                                            "                                    WHEN a.ENGIN = 'ON' AND a.SPEED <= 0" +
//                                            "                                    THEN 2" +
//                                            "                                    WHEN a.ENGIN = 'OFF'" +
//                                            "                                    THEN 3" +
//                                            "                                    END iscolor" +
//                                            "from nex_individual_temp a," +
//                                            "     nex_individual_client b" +
//                                            "where a.vehicle_id = b.id" +
//                                            "  and a.VEHICLE_ID = ?" +
//                                            "  AND b.OPERATORID = ?" +
//                                            "  AND b.ACTIVATION = 1");
//                    return jdbcTemplate.query(query, new IndivisualAccVehicleListRowMapper(), id, operatorId);
//                } catch (Exception e) {
//
//                    logger.error(e.getCause().getMessage());
//                   /* throw new AppCommonException(e.getMessage());*/
//                }
//            default:
//                return "vehicle list is empty";
        }
    }

}
