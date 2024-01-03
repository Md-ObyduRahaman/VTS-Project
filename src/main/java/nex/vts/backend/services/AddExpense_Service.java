package nex.vts.backend.services;

import nex.vts.backend.models.responses.AddExpenseResponse;
import nex.vts.backend.models.responses.ExpenseResponse;
import nex.vts.backend.repoImpl.AddExpense_Imp;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

@Component
public class AddExpense_Service {
    AddExpenseResponse expenseResponse = new AddExpenseResponse();
    ExpenseResponse response = new ExpenseResponse();
    private AddExpense_Imp addExpenseImp;

    public AddExpense_Service(AddExpense_Imp addExpenseImp) {
        this.addExpenseImp = addExpenseImp;
    }

    public AddExpenseResponse addExpenseService(            String oparationType,
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
                                                            Integer operatorId,
                                                            String schemaName) throws SQLException {

        dateTime = dateTime.replace("-", "");
        String responseMassage = null;
        switch (operatorId) {

            case 1:/*TODO GP*/
            case 3:/*TODO M2M*/

                responseMassage = addExpenseImp.addExpenseForGpAndM2M(
                        oparationType,
                        profileType,
                        profileId,
                        parentProfileId,
                        vehicleId,
                        expenseId,
                        dateTime,
                        amount,
                        expenseDescription,
                        expenseRowId,
                        expenseUnit,
                        expenseUnitPrice,
                        schemaName);

                if (!responseMassage.isEmpty()) {

                    response.setExpenseSubmitted(true);
                    response.setMessage(responseMassage);
                    expenseResponse.setResponse(response);
                } else {

                    response.setExpenseSubmitted(false);
                    response.setMessage("Operation Did not Success for" + operatorId);
                    expenseResponse.setResponse(response);
                }
        }
        return expenseResponse;
    }

}
