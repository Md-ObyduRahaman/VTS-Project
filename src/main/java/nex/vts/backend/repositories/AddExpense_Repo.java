package nex.vts.backend.repositories;

import java.sql.SQLException;

public interface AddExpense_Repo {
    String addExpenseForGpAndM2M(
            String oparationType,
            Integer profileType,
            Integer profileId,
            Integer parentProfileId,
            String  vehicleId,
            Integer expenseId,
            String dateTime,
            String amount,
            String expenseDescription,
            String expenseRowId,
            Integer expenseUnit,
            Integer expenseUnitPrice,
            String schemaName
    ) throws SQLException;

/*    int addExpenseForM2M(String vehicleId, String profileId, String expenseId, String dateTime,
                         String amount, String description, Integer expenseId2, Integer deptId,String schemaName);*/


}
