package nex.vts.backend.repoImpl;

import nex.vts.backend.models.responses.MotherAccVehicleList;
import nex.vts.backend.repositories.AddExpense_Repo;
import nex.vts.backend.repositories.Vehicle_List_Repo;
import nex.vts.backend.services.Vehicle_List_Service;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class AddExpense_Imp implements AddExpense_Repo {

    private Vehicle_List_Repo vehicleListRepo;

    public AddExpense_Imp(Vehicle_List_Repo vehicleListRepo) {
        this.vehicleListRepo = vehicleListRepo;
    }

    @Override
    public Object addExpense(String vehicleId,Integer userType, String companyId, String expenseHeader, String expenseDate, String expenseNote,Integer oparatorId,String shcemaName, Integer expenseHeader2, Integer deptId) {

        switch (userType){
            case 1:
                List<MotherAccVehicleList> VehicleList = (List<MotherAccVehicleList>) vehicleListRepo.getVehicleList(Integer.valueOf(companyId),userType,oparatorId,shcemaName,deptId);
                VehicleList.forEach(motherAccVehicleList ->
                        System.out.println(motherAccVehicleList.getVehicleId())
                );

        }



        return Optional.empty();
    }

}
