package nex.vts.backend.services;

import nex.vts.backend.exceptions.AppCommonException;
import nex.vts.backend.models.responses.*;
import nex.vts.backend.repoImpl.Vehicle_List_Repo_Imp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Vehicle_List_Service {

    private final Logger logger = LoggerFactory.getLogger(Vehicle_List_Service.class);
    private final Vehicle_List_Repo_Imp Vehicle_List_Repo_Imp;

    MotherVehicleListResponse motherVehicleListResponse = new MotherVehicleListResponse();
    DepartmentVehicleListResponse departmentVehicleListResponse = new DepartmentVehicleListResponse();
    IndividualVehicleListResponse individualVehicleListResponse = new IndividualVehicleListResponse();

    public Vehicle_List_Service(Vehicle_List_Repo_Imp Vehicle_List_Repo_Imp) {
        this.Vehicle_List_Repo_Imp = Vehicle_List_Repo_Imp;
    }

    public Object getVehicleList(Integer id/*, String limit, Integer offset*/, Integer userType, Integer oparatorId,String shcemaName,Integer deptId) {
        try {
            return Vehicle_List_Repo_Imp.getVehicleList(id/*, limit, offset*/, userType,oparatorId, shcemaName,deptId);
        } catch (Exception e) {
            throw new AppCommonException(300 + "Vehicles are not registered yet of this acccount "+id+ " "+deptId);
        }
    }

    public Object getVehicles(Integer id,/* String limit, Integer offset,*/
                              Integer userType,
                              Integer operatorId,
                              String shcemaName,
                              Integer deptId,
                              Integer deviceType,
                              short apiVersion) {
//        VehicleListResponse vehicleListResponse = new VehicleListResponse();
/*        if (userType.equals(1)) {
            List<MotherAccVehicleList> totalVehicleList = (List<MotherAccVehicleList>) getVehicleList(id*//*, limit, offset*//*, userType, operatorId, shcemaName, deptId);
            motherVehicleListResponse.setMotherAccVehicleLists((List<MotherAccVehicleList>) getVehicleList(id*//*, limit, offset*//*, userType, operatorId, shcemaName, deptId));
            motherVehicleListResponse.setTotalVehicle(totalVehicleList.size());
        } else if (userType.equals(2)) {
            departmentVehicleListResponse.setDeptAccVehicleLists((List<DeptAccVehicleList>) getVehicleList(id*//*, limit, offset*//*, userType, operatorId, shcemaName, deptId));
            List<DeptAccVehicleList> totalDeptAccVehicleList = (List<DeptAccVehicleList>) getVehicleList(id*//*, limit, offset*//*, userType, operatorId, shcemaName, deptId);
            departmentVehicleListResponse.setTotalVehicle(totalDeptAccVehicleList.size());
        } else
             individualVehicleListResponse.setIndividualAccVehicleListList((IndividualAccVehicleList) getVehicleList(id*//*, limit, offset*//*, userType, operatorId, shcemaName, deptId));*/
        switch (userType) {
            case 1:/*TODO parent profile*/
                try {

                    List<MotherAccVehicleList> totalVehicleList = (List<MotherAccVehicleList>) getVehicleList(id/*, limit, offset*/, userType, operatorId, shcemaName, deptId);
                    motherVehicleListResponse.setMotherAccVehicleLists((List<MotherAccVehicleList>) getVehicleList(id/*, limit, offset*/, userType, operatorId, shcemaName, deptId));
                    motherVehicleListResponse.setTotalVehicle(totalVehicleList.size());
                    return motherVehicleListResponse;
                }
                catch (Exception e){

                    logger.error(e.getMessage());
                    throw new AppCommonException(600 + "##Could not fetch  vehicleList" + deviceType + "##" + apiVersion);
                }
            case 2:/*TODO child/department profile*/
                try {

                    departmentVehicleListResponse.setDeptAccVehicleLists((List<DeptAccVehicleList>) getVehicleList(id/*, limit, offset*/, userType, operatorId, shcemaName, deptId));
                    List<DeptAccVehicleList> totalDeptAccVehicleList = (List<DeptAccVehicleList>) getVehicleList(id/*, limit, offset*/, userType, operatorId, shcemaName, deptId);
                    departmentVehicleListResponse.setTotalVehicle(totalDeptAccVehicleList.size());
                    return departmentVehicleListResponse;
                }catch (Exception e){
                    logger.error(e.getMessage());
                    throw new AppCommonException(600 + "##Could not fetch  vehicleList" + deviceType + "##" + apiVersion);
                }
            case 3:/*TODO indivisual profile*/
                try {
                    individualVehicleListResponse.setIndividualAccVehicleListList((List<IndividualAccVehicleList>) getVehicleList(id/*, limit, offset*/, userType, operatorId, shcemaName, deptId));
                    return individualVehicleListResponse;
                }catch (Exception e)
                {
                    logger.error(e.getMessage());
                    throw new AppCommonException(600 + "##Could not fetch  vehicleList" + deviceType + "##" + apiVersion);
                }
            default:
                return "this" + userType + " is not registered";
        }
//        return null;
    }


}
