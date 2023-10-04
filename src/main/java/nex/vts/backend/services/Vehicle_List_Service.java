package nex.vts.backend.services;

import nex.vts.backend.repoImpl.Vehicle_List_Repo_Imp;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class Vehicle_List_Service {
    public Vehicle_List_Service(Vehicle_List_Repo_Imp Vehicle_List_Repo_Imp) {
        this.Vehicle_List_Repo_Imp = Vehicle_List_Repo_Imp;
    }

    private final Vehicle_List_Repo_Imp Vehicle_List_Repo_Imp;

    public List<Object> getVehicleList(Integer groupId, Integer operationId, String limit, Integer offset, Integer userType, Integer parentId) {
        return Collections.singletonList(Vehicle_List_Repo_Imp.getVehicleList(groupId, operationId, limit, offset, userType, parentId));
    }

    public Object get_total_vehicle(Integer groupId, Integer parentId, Integer userType) {
        Object total_vehicle;
        if (userType.equals(1))
            total_vehicle = Vehicle_List_Repo_Imp.total_vehicle_for_user_type_1(groupId);
        else if (userType.equals(2))
            total_vehicle = Vehicle_List_Repo_Imp.total_vehicle_for_user_type_2(groupId, parentId);
        else if (userType.equals(3))
            total_vehicle = Vehicle_List_Repo_Imp.total_vehicle_for_user_type_3(groupId);
        else
            total_vehicle = Vehicle_List_Repo_Imp.total_vehicle_for_user_type_default(groupId, parentId, userType);
        return total_vehicle;
    }
}
