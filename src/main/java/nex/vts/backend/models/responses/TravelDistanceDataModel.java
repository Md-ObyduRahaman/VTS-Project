package nex.vts.backend.models.responses;

import lombok.Data;
import nex.vts.backend.models.requests.LoginReq;

@Data
public class TravelDistanceDataModel extends LoginReq {

    private String querymonthYear,p_alert_type,p_report_type;
    private Integer vehicleId,p_all_vehicle_flag,p_date_to,p_date_from;
}
