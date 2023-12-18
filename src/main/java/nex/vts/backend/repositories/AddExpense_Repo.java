package nex.vts.backend.repositories;

public interface AddExpense_Repo {
    int addExpenseForGp(String vehicleId, String profileId, String expenseId, String dateTime,
                   String amount, String description, Integer expenseId2, Integer deptId,String schemaName);

    int addExpenseForM2M(String vehicleId, String profileId, String expenseId, String dateTime,
                         String amount, String description, Integer expenseId2, Integer deptId,String schemaName);
}
