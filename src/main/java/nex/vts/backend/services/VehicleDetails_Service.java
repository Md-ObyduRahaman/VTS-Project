package nex.vts.backend.services;

import nex.vts.backend.exceptions.AppCommonException;
import nex.vts.backend.models.responses.VehicleDetailInfo;
import nex.vts.backend.models.responses.VehicleInfoResponse;
import nex.vts.backend.repositories.VehicleDetails_Repo;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class VehicleDetails_Service {
    private final VehicleDetails_Repo vehicleDetailsRepo;

    private final short API_VERSION = 1;

    VehicleInfoResponse infoResponse = new VehicleInfoResponse();
    VehicleDetailInfo vehicleDetailInfo = null;

    public VehicleDetails_Service(VehicleDetails_Repo vehicleDetailsRepo) {
        this.vehicleDetailsRepo = vehicleDetailsRepo;
    }

    public VehicleInfoResponse getVehicleDetail(Integer vehicleId, String schemaName, Integer operatorId) {

        switch (operatorId) {
            case 1:/*TODO Gp*/
            case 3:/*TODO M2M*/

                VehicleDetailInfo detailInfo = (VehicleDetailInfo) vehicleDetailsRepo.getVehicleDetailForGpAndM2M(vehicleId, schemaName);
                if (!detailInfo.equals(null)){

                    infoResponse.setVehicleDetailInfo(detailInfo);
                    infoResponse.setMassage("success");
                }
                else{
                    infoResponse.setVehicleDetailInfo(vehicleDetailInfo);
                    infoResponse.setMassage("success");
                }
        }

        return infoResponse;

    }

}
