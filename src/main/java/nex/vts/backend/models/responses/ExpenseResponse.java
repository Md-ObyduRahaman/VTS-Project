package nex.vts.backend.models.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ExpenseResponse {

    @JsonProperty("code")
    private int code;

    @JsonProperty("expenseSubmitted")
    private boolean expenseSubmitted;

    @JsonProperty("message")
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

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
        return "Response{" + "code = '" + code + '\'' + ",expenseSubmitted = '" + expenseSubmitted + '\'' + ",message = '" + message + '\'' + "}";
    }
}