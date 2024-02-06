package nex.vts.backend.services;

import nex.vts.backend.models.responses.VehiclePositionReportResponse;
import nex.vts.backend.repositories.VehiclePositionRepo;
import org.springframework.stereotype.Service;

@Service
public class VehiclePositionReport_Service {
    private VehiclePositionRepo positionRepo;
    VehiclePositionReportResponse positionReportResponse = new VehiclePositionReportResponse();

    public VehiclePositionReport_Service(VehiclePositionRepo positionRepo) {
        this.positionRepo = positionRepo;
    }

    public VehiclePositionReportResponse getPositionReportResponse(String userId, String p_userId, Integer vehicleId,
                                                                   String fromDate, String toDate, String locationStat, int deviceType,
                                                                   int userType, int offSet, int limit){

      positionReportResponse.setReportData(positionRepo.findVehiclePositionRepo(userId, p_userId, vehicleId, fromDate, toDate,
                locationStat, deviceType, userType, offSet, limit));

      positionReportResponse.setNumberOfRecords(positionRepo.findVehiclePositionRepo(userId, p_userId, vehicleId, fromDate, toDate,
              locationStat, deviceType, userType, offSet, limit).size());

      return positionReportResponse;
    }
}
