package nex.vts.backend.models.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ExpenseResponse {

    @JsonProperty("expenseSubmitted")
    private boolean expenseSubmitted;

    @JsonProperty("message")
    private String message;

    public boolean isExpenseSubmitted() {
        return expenseSubmitted;
    }

    public void setExpenseSubmitted(boolean expenseSubmitted) {
        this.expenseSubmitted = expenseSubmitted;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ExpenseResponse{" +
                "expenseSubmitted=" + expenseSubmitted +
                ", message='" + message + '\'' +
                '}';
    }
}