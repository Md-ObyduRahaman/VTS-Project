package nex.vts.backend.repositories;

import java.util.Optional;

public interface AddExpense_Repo {
    Object addExpense(String vehicleId,Integer usertype, String companyId, String expenseHeader, String expenseDate, String expenseNote,Integer oparatorId,String shcemaName, Integer expenseHeader2, Integer deptId);
}
