package nex.vts.backend.repositories;

import java.util.Optional;

public interface Vehicle_List_Repo {
    Object getVehicleList(Integer id, Integer userType, Integer operatorId, String vehicleID_MotherAccId, Integer deptId);
}
