package nex.vts.backend.repositories;

import java.util.List;

public interface Vehicle_List_Repo {
    List<Object> getVehicleList(Integer groupId,
                                Integer operationId,
                                String limit,
                                Integer offset,
                                Integer userType,
                                Integer parentId);
}
