package nex.vts.backend.models.responses;


import lombok.Data;
import nex.vts.backend.models.requests.LoginReq;

@Data
public class SpeedDataModel extends LoginReq {
    private String fromDate;
    private Integer timeSlot,vehicleId;
}
