package nex.vts.backend.services;

import nex.vts.backend.exceptions.AppCommonException;
import nex.vts.backend.models.responses.VehicleListResponse;
import nex.vts.backend.models.responses.VehiclesItem;
import nex.vts.backend.repoImpl.Vehicle_List_Repo_Imp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Vehicle_List_Service {

    private final Logger logger = LoggerFactory.getLogger(Vehicle_List_Service.class);
    private final Vehicle_List_Repo_Imp Vehicle_List_Repo_Imp;

    public Vehicle_List_Service(Vehicle_List_Repo_Imp Vehicle_List_Repo_Imp) {
        this.Vehicle_List_Repo_Imp = Vehicle_List_Repo_Imp;
    }

    public List<VehiclesItem> getVehicleList(Integer groupId, String limit, Integer offset, Integer userType, Integer parentId) {
        try {
            System.out.println("debug point 1");
            return Vehicle_List_Repo_Imp.getVehicleList(groupId, limit, offset, userType, parentId);
        } catch (Exception e) {
            throw new AppCommonException(e.getMessage());
        }
    }

    public Integer get_total_vehicle(Integer groupId, Integer parentId, Integer userType) {
        Integer total_vehicle;
        if (userType.equals(1)) total_vehicle = Vehicle_List_Repo_Imp.total_vehicle_for_user_type_1(groupId);
        else if (userType.equals(2))
            total_vehicle = Vehicle_List_Repo_Imp.total_vehicle_for_user_type_2(groupId, parentId);
        else if (userType.equals(3)) total_vehicle = Vehicle_List_Repo_Imp.total_vehicle_for_user_type_3(groupId);
        else total_vehicle = Vehicle_List_Repo_Imp.total_vehicle_for_user_type_default(groupId, parentId, userType);
        return total_vehicle;
    }

    public VehicleListResponse getVehicles(Integer groupId, String limit, Integer offset, Integer userType, Integer parentId) {
        VehicleListResponse vehicleListResponse = new VehicleListResponse();
        vehicleListResponse.totalVehicle = get_total_vehicle(groupId, parentId, userType);
        vehicleListResponse.vehicles = getVehicleList(groupId, limit, offset, userType, parentId);
        return vehicleListResponse;
    }


}
