package nex.vts.backend.repositories;

import java.util.List;

public interface Vehicle_List_Repo {
    Object getVehicleList(Integer groupId, String limit, Integer offset, Integer userType, Integer parentId);
}
