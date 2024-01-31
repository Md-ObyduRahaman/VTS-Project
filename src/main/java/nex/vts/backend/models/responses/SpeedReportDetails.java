package nex.vts.backend.models.responses;

import lombok.Data;

@Data
public class SpeedReportDetails {

    private String sl,vehId,vehName,dateTime,locationDetails,vehSpeed,unit;
}
