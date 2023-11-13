package nex.vts.backend.services;

import nex.vts.backend.repoImpl.Vehicle_Get_Expense_List_Repo_Imp;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class Vehicle_Expense_List {
    private final Vehicle_Get_Expense_List_Repo_Imp listRepoImp;

    public Vehicle_Expense_List(Vehicle_Get_Expense_List_Repo_Imp listRepoImp) {
        this.listRepoImp = listRepoImp;
    }

    public Optional<Object> getExpenseList(){
        return listRepoImp.getExpenseList();
    }

}
