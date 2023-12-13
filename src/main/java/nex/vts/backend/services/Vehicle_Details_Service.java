package nex.vts.backend.services;

import nex.vts.backend.models.responses.VehicleDetails;
import nex.vts.backend.models.responses.VehicleDetailsResponse;
import nex.vts.backend.repositories.Vehicle_Details_Repo;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

@Component
public class Vehicle_Details_Service {
//    private final Vehicle_DetailsPermission_Repo_Imp permissionRepoImp;

    private final Vehicle_Details_Repo vehicleDetailsRepo;

    public Vehicle_Details_Service(Vehicle_Details_Repo vehicleDetailsRepo) {
        this.vehicleDetailsRepo = vehicleDetailsRepo;
    }

/*    public Vehicle_Details_Service(Vehicle_DetailsPermission_Repo_Imp permissionRepoImp) {
        this.permissionRepoImp = permissionRepoImp;
    }*/


    public VehicleDetailsResponse getVehicleDetails(Integer userType, Integer profileId,String schemaName) throws SQLException {
        VehicleDetailsResponse vehicleDetailsResponse = new VehicleDetailsResponse();
        vehicleDetailsResponse.vehicleDetails = (VehicleDetails) vehicleDetailsRepo.getVehicleDetails(userType, profileId,schemaName);
        return vehicleDetailsResponse;
    }
/*    public Object getVehiclePermission(Integer userType, Integer profileId, Integer parentId, Integer vehicleId) {
        return permissionRepoImp.getVehiclePermission(userType, profileId, parentId, vehicleId);
    }*/

}