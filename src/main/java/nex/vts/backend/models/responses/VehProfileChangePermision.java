package nex.vts.backend.models.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehProfileChangePermision {

    @JsonProperty("id")
    private short ID;
    @JsonProperty("is_veh_pass_change")
    private short IS_VEH_PASS_CHANGE;
    @JsonProperty("is_veh_stat_change")
    private short IS_VEH_STAT_CHANGE;
    @JsonProperty("is_veh_speed_change")
    private short IS_VEH_SPEED_CHANGE;
    @JsonProperty("is_veh_alert_mail_change")
    private short IS_VEH_ALERT_MAIL_CHANGE;
    @JsonProperty("is_veh_alert_sms_change")
    private short IS_VEH_ALERT_SMS_CHANGE;
    @JsonProperty("is_veh_d_profile_change")
    private short IS_VEH_D_PROFILE_CHANGE;
}
