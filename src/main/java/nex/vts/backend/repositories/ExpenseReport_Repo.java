package nex.vts.backend.repositories;

public interface ExpenseReport_Repo {
    Object getExpenseInfo(String groupId,String userId,String fromDate,String toDate,String schemaName);
}
