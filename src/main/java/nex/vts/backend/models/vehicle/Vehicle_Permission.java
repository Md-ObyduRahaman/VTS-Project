package nex.vts.backend.models.vehicle;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
@Getter
@Setter
@ToString
public class Vehicle_Permission {
    private Integer email, sms, isSafeModeActive, isMultipleNAllow;
    private String maxCarSpeed;
    private Integer  isIndGeoEnable, isIndSpdEnable;
    private Integer isChangeSms, isChangeEmail, isChangeMaxSpeed;

    public Vehicle_Permission(Integer email, Integer sms, Integer isSafeModeActive, Integer isMultipleNAllow, String maxCarSpeed, Integer isChangeSms, Integer isChangeEmail, Integer isChangeMaxSpeed, Integer isIndGeoEnable, Integer isIndSpdEnable) {
        this.email = email;
        this.sms = sms;
        this.isSafeModeActive = isSafeModeActive;
        this.isMultipleNAllow = isMultipleNAllow;
        this.maxCarSpeed = maxCarSpeed;
        this.isChangeSms = isChangeSms;
        this.isChangeEmail = isChangeEmail;
        this.isChangeMaxSpeed = isChangeMaxSpeed;
        this.isIndGeoEnable = isIndGeoEnable;
        this.isIndSpdEnable = isIndSpdEnable;
    }

    public Vehicle_Permission() {
    }
}
