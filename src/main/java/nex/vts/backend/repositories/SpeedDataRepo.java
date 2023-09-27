package nex.vts.backend.repositories;



import nex.vts.backend.models.responses.SpeedDataResponse;

import java.util.ArrayList;
import java.util.Optional;

public interface SpeedDataRepo {

    public Optional<ArrayList<SpeedDataResponse>> getSpeedDataForhr(String finalToTime, String finalFromTime,Integer vehicleId);

}
