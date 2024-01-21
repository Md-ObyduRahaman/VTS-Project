package nex.vts.backend.models.responses;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VehiclePositionReportData {
    private String sl,vehId,vehName,dateTime,locationDetails,engStat;

}
