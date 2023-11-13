package nex.vts.backend.models.responses;


import lombok.AllArgsConstructor;
import lombok.Data;
import nex.vts.backend.models.requests.LoginReq;

@Data
@AllArgsConstructor
public class SpeedDataModel  {
    private String fromDate;
    private Integer timeSlot,vehicleId;
}
