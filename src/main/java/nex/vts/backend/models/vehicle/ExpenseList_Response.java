package nex.vts.backend.models.vehicle;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ExpenseList_Response {
    @JsonProperty(value = "Expense-List")
    public Object expenseList;
}
