package nex.vts.backend.services;

import nex.vts.backend.models.responses.VehicleDetailInfo;
import nex.vts.backend.models.responses.VehicleInfoResponse;
import nex.vts.backend.repositories.VehicleDetails_Repo;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

@Component
public class VehicleDetails_Service {
    private final VehicleDetails_Repo vehicleDetailsRepo;

    VehicleInfoResponse infoResponse = new VehicleInfoResponse();

    public VehicleDetails_Service(VehicleDetails_Repo vehicleDetailsRepo) {
        this.vehicleDetailsRepo = vehicleDetailsRepo;
    }

    public VehicleInfoResponse getVehicleDetail(Integer vehicleId, String schemaName, Integer operatorId) {

        switch (operatorId) {
            case 1:/*TODO Gp*/
            case 3:/*TODO M2M*/
                VehicleDetailInfo detailInfo = (VehicleDetailInfo) vehicleDetailsRepo.getVehicleDetailForGpAndM2M(vehicleId, schemaName);
                infoResponse.setVehicleDetailInfo(detailInfo);
        }

        return infoResponse;

    }

}
