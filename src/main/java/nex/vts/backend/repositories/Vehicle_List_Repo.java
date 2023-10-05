package nex.vts.backend.repositories;

import nex.vts.backend.models.vehicle.Vehicle_List;

import java.util.List;

public interface Vehicle_List_Repo {
    List<Vehicle_List> getVehicleList(Integer groupId,
                                      Integer operationId,
                                      String limit,
                                      Integer offset,
                                      Integer userType,
                                      Integer parentId);
}
