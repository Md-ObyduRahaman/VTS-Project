package nex.vts.backend.repositories;



import nex.vts.backend.models.responses.SpeedDataResponse;
import nex.vts.backend.models.responses.TravelDistanceDataModel;

import java.util.ArrayList;
import java.util.Optional;

public interface TravelDistanceDataRepo {

    public Optional<ArrayList<TravelDistanceDataModel>> getTravelDistanceData(TravelDistanceDataModel travelDistanceDataModel);

}
