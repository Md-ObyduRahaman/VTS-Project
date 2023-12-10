package nex.vts.backend.services;

import nex.vts.backend.dbentities.VTS_LOGIN_USER;
import nex.vts.backend.models.responses.AddExpenseResponse;
import nex.vts.backend.models.responses.ExpenseResponse;
import nex.vts.backend.repoImpl.AddExpense_Imp;
import org.springframework.stereotype.Service;

@Service
public class AddExpense_Service {

    private AddExpense_Imp addExpenseImp;

    AddExpenseResponse expenseResponse = new AddExpenseResponse();
    ExpenseResponse response = new ExpenseResponse();

    public AddExpense_Service(AddExpense_Imp addExpenseImp) {
        this.addExpenseImp = addExpenseImp;
    }

/*    public Object adExpenseService( String expenseNote, String schemaName, VTS_LOGIN_USER loginUser){
        System.out.println(loginUser);
        return addExpenseImp.addExpense(expenseNote,schemaName,loginUser);
    }*/

    public AddExpenseResponse addExpenseService(String userId, String groupId, String expenseId, String dateTime, String amount, String schemaName, String description, Integer expenseId2, Integer deptId){
        dateTime = dateTime.replace("-","");
        try {
            addExpenseImp.addExpense(userId, groupId, expenseId, dateTime, amount,schemaName, description, expenseId2, deptId);
            response.setExpenseSubmitted(true);
            response.setMessage("Operation Success");
            expenseResponse.setResponse(response);
        }catch (Exception e){
            response.setExpenseSubmitted(false);
            response.setMessage("Operation Did not Sucess");
            expenseResponse.setResponse(response);
        }
        return expenseResponse;
    }
}
