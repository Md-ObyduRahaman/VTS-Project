package nex.vts.backend.repositories;

import nex.vts.backend.models.vehicle.vehicleList;

import java.util.List;

public interface vehicleListRepository {
    List<vehicleList> getVehicleList(Integer groupId, Integer operationId, String limit, Integer offset, Integer userType, Integer parentId);
}
