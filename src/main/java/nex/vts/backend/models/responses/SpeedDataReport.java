package nex.vts.backend.models.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpeedDataReport {

    private  String ROWNUM, GROUPID, VEHICLEID, DATETIME, NUM_OF_DAYS,
            DISTANCE, MOTHER_ACCOUNT_NAME, VEHICLE_NAME, COMPANY_NAME;
}
