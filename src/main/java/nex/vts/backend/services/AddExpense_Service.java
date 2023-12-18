package nex.vts.backend.services;

import nex.vts.backend.models.responses.AddExpenseResponse;
import nex.vts.backend.models.responses.ExpenseResponse;
import nex.vts.backend.repoImpl.AddExpense_Imp;
import org.springframework.stereotype.Service;

@Service
public class AddExpense_Service {
    AddExpenseResponse expenseResponse = new AddExpenseResponse();
    ExpenseResponse response = new ExpenseResponse();
    private AddExpense_Imp addExpenseImp;

    public AddExpense_Service(AddExpense_Imp addExpenseImp) {
        this.addExpenseImp = addExpenseImp;
    }

    public AddExpenseResponse addExpenseService(String vehicleId, String profileId, String expenseId, String dateTime, String amount, String description, Integer expenseId2, Integer deptId, Integer operatorId,String schemaName) {

        dateTime = dateTime.replace("-", "");
        int flag = 0;
        switch (operatorId) {

            case 1:/*TODO GP*/
                flag = addExpenseImp.addExpenseForGp(vehicleId, profileId, expenseId, dateTime, amount, description, expenseId2, deptId,schemaName);

                if (flag == 1) {
                    response.setExpenseSubmitted(true);
                    response.setMessage("Operation Success");
                } else {
                    response.setExpenseSubmitted(false);
                    response.setMessage("Operation Did not Success for Gp");
                }
            case 3:/*TODO M2M*/
                flag = addExpenseImp.addExpenseForM2M(vehicleId, profileId, expenseId, dateTime, amount, description, expenseId2, deptId,schemaName);

                if (flag == 1) {
                    response.setExpenseSubmitted(true);
                    response.setMessage("Operation Success");
                    expenseResponse.setResponse(response);
                } else {
                    response.setExpenseSubmitted(false);
                    response.setMessage("Operation Did not Success for M2M");
                    expenseResponse.setResponse(response);
                }
        }
        return expenseResponse;
    }

}
