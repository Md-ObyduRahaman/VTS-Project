package nex.vts.backend.models.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class VehicleOthersInfoModel {

    private String  IND_PASS;
    @JsonProperty("cellPhone")
    private String CELL_PHONE;
    @JsonProperty("emailId")
    private String       EMAIL;
    @JsonProperty("isFavorite")
    private Boolean IS_FAVORITE;
    @JsonProperty("vehicleStatus")
    private Integer VEHICLE_STATUS;
    @JsonProperty("multipleNotification")
    private boolean IS_MULTIPLE_NOTIFICATION_ALLOW;
    @JsonProperty("safeMode")
    private Integer IS_SAFE_MODE_ACTIVE;
    @JsonProperty("maxSpeedValue")
    private Integer MAX_CAR_SPEED;

    public Boolean getIS_MULTIPLE_NOTIFICATION_ALLOW()
    {
        return this.IS_MULTIPLE_NOTIFICATION_ALLOW;
    }

}
