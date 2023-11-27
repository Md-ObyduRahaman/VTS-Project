package nex.vts.backend.services;

import nex.vts.backend.exceptions.AppCommonException;
import nex.vts.backend.models.responses.DeptOfVehicleListItem;
import nex.vts.backend.models.responses.DetailsOfVehicleItem;
import nex.vts.backend.models.responses.VehicleListResponse;
import nex.vts.backend.repoImpl.Vehicle_List_Repo_Imp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Vehicle_List_Service {

    private final Logger logger = LoggerFactory.getLogger(Vehicle_List_Service.class);
    private final Vehicle_List_Repo_Imp Vehicle_List_Repo_Imp;

    public Vehicle_List_Service(Vehicle_List_Repo_Imp Vehicle_List_Repo_Imp) {
        this.Vehicle_List_Repo_Imp = Vehicle_List_Repo_Imp;
    }

    public Object getVehicleList(Integer id/*, String limit, Integer offset*/, Integer userType, Integer oparatorId,String shcemaName,Integer deptId) {
        try {
            return Vehicle_List_Repo_Imp.getVehicleList(id/*, limit, offset*/, userType,oparatorId, shcemaName,deptId);
        } catch (Exception e) {
            throw new AppCommonException(e.getMessage());
        }
    }

    public VehicleListResponse getVehicles(Integer id,/* String limit, Integer offset,*/ Integer userType,Integer operatorId,String shcemaName,Integer deptId) {
        VehicleListResponse vehicleListResponse = new VehicleListResponse();
        if(!userType.equals(2)){
            vehicleListResponse.setDetailsOfVehicle((List<DetailsOfVehicleItem>) getVehicleList(id/*, limit, offset*/, userType,operatorId,shcemaName,deptId));
        }else
            vehicleListResponse.setDeptOfVehicleList((List<DeptOfVehicleListItem>) getVehicleList(id/*, limit, offset*/, userType,operatorId,shcemaName,deptId));
        return vehicleListResponse;
    }


}
