package nex.vts.backend.models.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AddExpenseResponse {

    @JsonProperty("response")
    private ExpenseResponse expenseResponse;

    public ExpenseResponse getResponse() {
        return expenseResponse;
    }

    public void setResponse(ExpenseResponse expenseResponse) {
        this.expenseResponse = expenseResponse;
    }

    @Override
    public String toString() {
        return "AddExpenseResponse{" + "response = '" + expenseResponse + '\'' + "}";
    }
}