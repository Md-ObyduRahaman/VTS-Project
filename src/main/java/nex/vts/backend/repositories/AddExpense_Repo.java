package nex.vts.backend.repositories;

import javax.sql.DataSource;

public interface AddExpense_Repo {
    int addExpenseForGp(
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
    );

    int addExpenseForM2M(String vehicleId, String profileId, String expenseId, String dateTime,
                         String amount, String description, Integer expenseId2, Integer deptId,String schemaName);


}
