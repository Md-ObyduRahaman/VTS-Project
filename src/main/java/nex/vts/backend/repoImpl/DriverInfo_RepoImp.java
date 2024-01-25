package nex.vts.backend.repoImpl;

import nex.vts.backend.exceptions.AppCommonException;
import nex.vts.backend.models.responses.*;
import nex.vts.backend.repositories.DriverInfoRepo;
import nex.vts.backend.utilities.VtsDateTimeLib;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.TransientDataAccessException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
@Repository
public class DriverInfo_RepoImp implements DriverInfoRepo {

    private final Logger logger = LoggerFactory.getLogger(DriverInfo_RepoImp.class);

    private final short API_VERSION = 1;


    private final JdbcTemplate jdbcTemplate;
    @Autowired
    Environment environment;

    public DriverInfo_RepoImp(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }



    @Override
    public Optional<ArrayList<VehDriverInfo>> get_DriverInfo(String userId, int vehRowId, int deviceType, int userType) {

        Optional<ArrayList<VehDriverInfo>> data_List;

        //SET:: Main Query ---
        String sel_Query = "select "+
                                "ID, USERID,"+
                                "D_NAME, D_FNAME, D_LICENSE,"+
                                "D_ADDRESS, D_CELL, D_DOB, D_NID," +
                                "D_IMAGE," +
                                "MAX_CAR_SPEED," +
                                "DRIVER_PHOTO,DRIVER_LICENSE " +
                        "FROM nex_driverinfo " +
                        "WHERE (USERID = '" + vehRowId + "')";

        System.out.println(sel_Query);

        try {
            //
            ArrayList<VehDriverInfo> vehDriverInfo_List = (ArrayList<VehDriverInfo>) jdbcTemplate.query(sel_Query, (rs, rowNum) -> {

                VehDriverInfo vehDriverInfo = new VehDriverInfo();

                vehDriverInfo.setDriverRowId(rs.getString("ID"));
                vehDriverInfo.setDriverName(rs.getString("D_NAME"));
                vehDriverInfo.setDriverFather(rs.getString("D_FNAME"));
                vehDriverInfo.setDriverAddress(rs.getString("D_ADDRESS"));
                vehDriverInfo.setDriverCell(rs.getString("D_CELL"));
                vehDriverInfo.setDriverNID(rs.getString("D_NID"));

                String _xy_DriverDOB =VtsDateTimeLib.get_DateFormatter(rs.getString("D_DOB"));
                vehDriverInfo.setDriverDOB(_xy_DriverDOB);

                return vehDriverInfo;
            });

            data_List = Optional.ofNullable(vehDriverInfo_List);
            //
        } catch (BadSqlGrammarException e) {
            logger.trace("No Data found with userId is {}  Sql Grammar Exception", userId);
            throw new AppCommonException(4001 + "##Sql Grammar Exception" + deviceType + "##" + API_VERSION);
            //
        } catch (TransientDataAccessException f) {
            logger.trace("No Data found with userId is {} network or driver issue or db is temporarily unavailable  ", userId);
            throw new AppCommonException(4002 + "##Network or driver issue or db is temporarily unavailable" + deviceType + "##" + API_VERSION);
            //
        } catch (CannotGetJdbcConnectionException g) {
            logger.trace("No Data found with userId is {} could not acquire a jdbc connection  ", userId);
            throw new AppCommonException(4003 + "##A database connection could not be obtained" + deviceType + "##" + API_VERSION);
        }

        return data_List;
    }




    //    public Optional<DriverInfoModel> findDriverInfo(Integer USERID) {
//        logger.debug("Executing query to get client profile by client user id: {}", USERID);
//
//        String shcemaName = environment.getProperty("application.profiles.shcemaName");
//
//        Optional<DriverInfoModel> getAllDriverInfo=Optional.empty();
//
//            String sql = "SELECT ID,\n" +
//                    "       USERID,\n" +
//                    "       D_NAME,\n" +
//                    "       D_FNAME,\n" +
//                    "       D_LICENSE,\n" +
//                    "       D_ADDRESS,\n" +
//                    "       D_CELL,\n" +
//                    "       "+shcemaName+"date_formate_convert (D_DOB, 'YYYYMMDD', 'DD-Mon-YYYY')    D_DOB,\n" +
//                    "       LENGTH (DRIVER_PHOTO)                                      DRIVER_PHOTO\n" +
//                    "  FROM NEX_DRIVERINFO\n" +
//                    " WHERE USERID ="+USERID;
//
//                   /* jdbcTemplate.queryForObject(sql, new Object[]{USERID}, (rs, rowNum) ->
//                            Optional.of(new DriverInfoModel(
//                                    rs.getString("ID"),
//                                    rs.getString("USERID"),
//                                    rs.getString("D_NAME"),
//                                    rs.getString("D_FNAME"),
//                                    rs.getString("D_LICENSE"),
//                                    rs.getString("D_ADDRESS"),
//                                    rs.getString("D_CELL"),
//                                    rs.getString("D_DOB"),
//                                    rs.getBoolean("DRIVER_PHOTO")
//                            ))
//            );*/
//
//         Optional<DriverInfoModel> getExpenseList=Optional.empty();
//        try {
//            getAllDriverInfo = Optional.of((DriverInfoModel) jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(DriverInfoModel.class)));
//        }
//        catch (BadSqlGrammarException e) {
//            e.printStackTrace();
//            logger.trace("No Data found with vehicleId is {}  Sql Grammar Exception", USERID);
//            throw new AppCommonException(4001 + "##Sql Grammar Exception##1##1");
//        } catch (TransientDataAccessException f) {
//            logger.trace("No Data found with vehicleId is {} network or driver issue or db is temporarily unavailable  ", USERID);
//            throw new AppCommonException(4002 + "##Network or driver issue or db is temporarily unavailable");
//        } catch (CannotGetJdbcConnectionException g) {
//            logger.trace("No Data found with vehicleId is {} could not acquire a jdbc connection  ", USERID);
//            throw new AppCommonException(4003 + "##A database connection could not be obtained");
//        }
//
//        if (getAllDriverInfo.isEmpty()) {
//            return Optional.empty();
//        } else {
//            return getAllDriverInfo;
//        }
//
//    }
//    public Optional<VehProfileChangePermision> findVehProfileChangePermision(Integer vehicle_id,Integer profile_type) {
//        logger.debug("Executing query to get client profile by client vehicle_id: {}", vehicle_id);
//
//
//        Optional<VehProfileChangePermision> vehProfileChangePermisionList=Optional.empty();
//
//            String sql = "SELECT ID,\n" +
//                    "       IS_VEH_PASS_CHANGE,\n" +
//                    "       IS_VEH_STAT_CHANGE,\n" +
//                    "       IS_VEH_SPEED_CHANGE,\n" +
//                    "       IS_VEH_ALERT_MAIL_CHANGE,\n" +
//                    "       IS_VEH_ALERT_SMS_CHANGE,\n" +
//                    "       IS_VEH_D_PROFILE_CHANGE\n" +
//                    "  FROM VEH_PROFILE_CHANGE_PERMISION\n" +
//                    " WHERE     VEHICLE_ID = "+vehicle_id+"\n" +
//                    "       AND PROFILE_TYPE = "+profile_type+"\n" +
//                    "       AND PROFILE_ID = "+vehicle_id;
//
//
//
//         //Optional<VehProfileChangePermision> vehProfileChangePermisionList=Optional.empty();
//        try {
//            vehProfileChangePermisionList = Optional.of((VehProfileChangePermision) jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(VehProfileChangePermision.class)));
//        }
//        catch (BadSqlGrammarException e) {
//            e.printStackTrace();
//            logger.trace("No Data found with vehicleId is {}  Sql Grammar Exception", vehicle_id);
//            throw new AppCommonException(4001 + "##Sql Grammar Exception##1##1");
//        } catch (TransientDataAccessException f) {
//            logger.trace("No Data found with vehicleId is {} network or driver issue or db is temporarily unavailable  ", vehicle_id);
//            throw new AppCommonException(4002 + "##Network or driver issue or db is temporarily unavailable");
//        } catch (CannotGetJdbcConnectionException g) {
//            logger.trace("No Data found with vehicleId is {} could not acquire a jdbc connection  ", vehicle_id);
//            throw new AppCommonException(4003 + "##A database connection could not be obtained");
//        }
//
//        if (vehProfileChangePermisionList.isEmpty()) {
//            return Optional.empty();
//        } else {
//            return vehProfileChangePermisionList;
//        }
//
//    }



}





