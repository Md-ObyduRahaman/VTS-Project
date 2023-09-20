package nex.vts.backend.models.responses;


import lombok.Data;
import lombok.NoArgsConstructor;
import nex.vts.backend.models.requests.LoginReq;

@Data
@NoArgsConstructor
public class DataForFvrtVehicle extends LoginResponse {
    //LoginReq
    private Integer offset,limit;
    private LoginReq loginReq;
}
