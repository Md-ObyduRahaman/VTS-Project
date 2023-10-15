package nex.vts.backend.repositories;

import java.sql.SQLException;

public interface VehicleSettingRepo {

    public Integer getDiffSettingInfo(String p_permision_type, Integer p_profile_type,Integer p_profile_id, Integer p_parent_profile_id, Integer p_vehicle_id);
    public String modify_vehicle_profile(
           String p_type              ,
           Integer  p_profile_type   ,
           Integer  p_profile_id        ,
           Integer  p_parent_profile_id ,
           Integer  p_vehicle_id        ,
           Integer  p_login_status      ,
           String  p_pass              ,
           Integer p_max_speed         ,
           String  p_sms               ,
           String  p_email             ,
           boolean  p_multiple_alert    ,
           Integer  p_safe_mode
    ) throws SQLException;

    public String manage_favorite_vehicle(
            String p_type              ,
            Integer p_profile_type     ,
            Integer p_profile_id        ,
            Integer p_parent_profile_id  ,
            Integer p_vehicle_id        ,
            Integer p_favorite_value
    ) throws SQLException;
}
