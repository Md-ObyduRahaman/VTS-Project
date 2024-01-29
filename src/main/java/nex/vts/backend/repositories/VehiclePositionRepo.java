package nex.vts.backend.repositories;



import nex.vts.backend.models.responses.VehiclePositionReportData;

import java.util.ArrayList;
import java.util.Optional;

public interface VehiclePositionRepo {

    public Optional<ArrayList<VehiclePositionReportData>> findVehiclePositionRepo(String userId,String p_userId, Integer vehicleId, String fromDate, String toDate, String locationStat,int deviceType,int userType);

}
