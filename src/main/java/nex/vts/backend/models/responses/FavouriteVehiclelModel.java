package nex.vts.backend.models.responses;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FavouriteVehiclelModel {


    private Long ID;
    private String VEHICLE_NAME, ENGINE_STATUS, SPEED,
            IS_FAVORITE, VEHICLE_ICON_TYPE, USER_DEFINED_VEHICLE_NAME,
            ICON_TYPE_ON_MAP, ICON_TYPE_RUNNING, ICON_TYPE_STOPPED,
            ICON_TYPE_STATIONARY, REGISTRATION_NUMBER, COLOR,
            VENDOR, MODEL, MAX_SPEED;
}
