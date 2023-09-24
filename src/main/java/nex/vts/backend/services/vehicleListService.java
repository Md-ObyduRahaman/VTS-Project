package nex.vts.backend.services;

import nex.vts.backend.repoImpl.vehicleListRepoImplementation;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class vehicleListService {
    public vehicleListService(vehicleListRepoImplementation vehicleListRepositoryImplementation) {
        this.vehicleListRepositoryImplementation = vehicleListRepositoryImplementation;
    }

    private final vehicleListRepoImplementation vehicleListRepositoryImplementation;

    public Optional getVehicleList(Integer groupId, Integer operatorId, String limit, Integer offset, Integer userType, Integer parentId) {
        return Optional.<Object>of(vehicleListRepositoryImplementation.getVehicleList(groupId, operatorId, limit, offset, userType, parentId).stream().findFirst());
    }

    public Optional get_total_vehicle(Integer groupId, Integer parentId, Integer userType) {
        Optional total_vehicle;
        if (userType.equals(1))
            total_vehicle = Optional.of(vehicleListRepositoryImplementation.total_vehicle_for_user_type_1(groupId));
        else if (userType.equals(2))
            total_vehicle = Optional.of(vehicleListRepositoryImplementation.total_vehicle_for_user_type_2(groupId, parentId));
        else if (userType.equals(3))
            total_vehicle = Optional.of(vehicleListRepositoryImplementation.total_vehicle_for_user_type_3(groupId));
        else
            total_vehicle = Optional.of(vehicleListRepositoryImplementation.total_vehicle_for_user_type_default(groupId, parentId, userType));
        return total_vehicle;
    }
}
