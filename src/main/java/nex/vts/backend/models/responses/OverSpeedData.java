package nex.vts.backend.models.responses;

import lombok.Data;

@Data
public class OverSpeedData {

    private String vehicleid, vehicle_name, alert_type, total_alert, rowno;
}
