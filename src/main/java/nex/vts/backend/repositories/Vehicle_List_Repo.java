package nex.vts.backend.repositories;

import java.util.List;
import java.util.Optional;

public interface Vehicle_List_Repo {
    Object getVehicleList(Integer id/*, String limit, Integer offset*/, Integer userType/*, Integer parentId*/,Integer operatorId,String vehicleID_MotherAccId);
}
