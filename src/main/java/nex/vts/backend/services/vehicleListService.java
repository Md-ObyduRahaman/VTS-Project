package nex.vts.backend.services;

import nex.vts.backend.repoImpl.vehicleListRepoImplementation;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class vehicleListService {
    public vehicleListService(vehicleListRepoImplementation vehicleListRepositoryImplementation) {
        this.vehicleListRepositoryImplementation = vehicleListRepositoryImplementation;
    }

    private final vehicleListRepoImplementation vehicleListRepositoryImplementation;

    public List<Object> getVehicleList(Integer groupId, Integer operationId, String limit, Integer offset, Integer userType, Integer parentId) {
        return Collections.singletonList(vehicleListRepositoryImplementation.getVehicleList(groupId, operationId, limit, offset, userType, parentId));
    }

    public Object get_total_vehicle(Integer groupId, Integer parentId, Integer userType) {
        Object total_vehicle;
        if (userType.equals(1))
            total_vehicle = vehicleListRepositoryImplementation.total_vehicle_for_user_type_1(groupId);
        else if (userType.equals(2))
            total_vehicle = vehicleListRepositoryImplementation.total_vehicle_for_user_type_2(groupId, parentId);
        else if (userType.equals(3))
            total_vehicle = vehicleListRepositoryImplementation.total_vehicle_for_user_type_3(groupId);
        else
            total_vehicle = vehicleListRepositoryImplementation.total_vehicle_for_user_type_default(groupId, parentId, userType);
        return total_vehicle;
    }
}
