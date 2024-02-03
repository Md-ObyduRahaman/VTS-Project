package nex.vts.backend.models.responses;

import lombok.Data;

import java.util.ArrayList;
import java.util.Optional;

@Data
public class VehicleStateReport {

    int numberOfRecord;
    Optional<ArrayList<VehicleStateInfoOra>> vehicleStateInfo;
}
