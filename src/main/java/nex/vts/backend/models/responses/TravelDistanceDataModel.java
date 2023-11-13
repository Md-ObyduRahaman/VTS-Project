package nex.vts.backend.models.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import nex.vts.backend.models.requests.LoginReq;

@Data
@AllArgsConstructor
public class TravelDistanceDataModel  {

    private String querymonthYear,p_alert_type,p_report_type;
    private Integer vehicleId,p_all_vehicle_flag,p_date_to,p_date_from;
    private  Integer parentId,profileId,profileType;



}
