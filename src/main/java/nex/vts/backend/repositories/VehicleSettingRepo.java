package nex.vts.backend.repositories;

public interface VehicleSettingRepo {

    public Integer getDiffSettingInfo(String p_permision_type, Integer p_profile_type,Integer p_profile_id, Integer p_parent_profile_id, Integer p_vehicle_id);
    public  String getModifyVicleProfileResponse(String p_type, Integer p_profile_type, Integer p_profile_id, Integer p_parent_profile_id, Integer p_vehicle_id, Integer p_login_status , String p_pass, String p_max_speed, String p_sms , String p_email , Integer p_multiple_alert, Integer p_safe_mode);
}
