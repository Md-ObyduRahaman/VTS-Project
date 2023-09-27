package nex.vts.backend.models.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class VehicleOthersInfoModel {

    private String  IND_PASS,
             CELL_PHONE, EMAIL;
    private Integer IS_FAVORITE, VEHICLE_STATUS,
            IS_MULTIPLE_NOTIFICATION_ALLOW,
            IS_SAFE_MODE_ACTIVE, MAX_CAR_SPEED;

}
