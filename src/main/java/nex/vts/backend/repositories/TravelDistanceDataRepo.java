package nex.vts.backend.repositories;



import nex.vts.backend.models.responses.MonthTravleDistanceForAll;
import nex.vts.backend.models.responses.SpeedDataResponse;
import nex.vts.backend.models.responses.TravelDistanceDataModel;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public interface TravelDistanceDataRepo {

    public MonthTravleDistanceForAll getTravelDistanceData(TravelDistanceDataModel travelDistanceDataModel,Integer deviceType) throws SQLException;

}
