package nex.vts.backend.services;

import nex.vts.backend.repoImpl.Vehicle_List_Repo_Imp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class Vehicle_List_Service {

    private final Logger logger = LoggerFactory.getLogger(Vehicle_List_Service.class);
    private final Vehicle_List_Repo_Imp Vehicle_List_Repo_Imp;
    Map<String, Object> respnse = new LinkedHashMap<>();

    public Vehicle_List_Service(Vehicle_List_Repo_Imp Vehicle_List_Repo_Imp) {
        this.Vehicle_List_Repo_Imp = Vehicle_List_Repo_Imp;
    }

    public Object getVehicleList(Integer groupId, String limit, Integer offset, Integer userType, Integer parentId) {
        try {
            return Vehicle_List_Repo_Imp.getVehicleList(groupId, limit, offset, userType, parentId);
        } catch (Exception e) {
            return logger;
        }
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

    public Object getVehicles(Integer groupId, String limit, Integer offset, Integer userType, Integer parentId) {
        respnse.put("Vehicle-list", getVehicleList(groupId, limit, offset, userType, parentId));
        respnse.put("Total-Vehicle", get_total_vehicle(groupId, parentId, userType));
        return respnse;
    }


}
