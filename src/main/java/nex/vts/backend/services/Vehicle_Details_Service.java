package nex.vts.backend.services;

import nex.vts.backend.repoImpl.Vehicle_DetailsPermission_Repo_Imp;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

@Component
public class Vehicle_Details_Service {
    private final Vehicle_DetailsPermission_Repo_Imp permissionRepoImp;

    public Vehicle_Details_Service(Vehicle_DetailsPermission_Repo_Imp permissionRepoImp) {
        this.permissionRepoImp = permissionRepoImp;
    }


    public Object getVehicleDetails(Integer userType, Integer profileId, Integer vehicleId) throws SQLException {
        return permissionRepoImp.getVehicleDetails(userType, profileId, vehicleId);
    }

    public Object getVehiclePermission(Integer userType, Integer profileId, Integer parentId, Integer vehicleId) {
        return permissionRepoImp.getVehiclePermission(userType, profileId, parentId, vehicleId);
    }
}
