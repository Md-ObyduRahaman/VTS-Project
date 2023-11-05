package nex.vts.backend.controllers;

import nex.vts.backend.models.responses.BaseResponse;
import nex.vts.backend.models.vehicle.ExpenseList_Response;
import nex.vts.backend.services.Vehicle_Expense_List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/private/v1")
public class VehicleExpense_Controller {
    private final Vehicle_Expense_List expenseList;
    ExpenseList_Response listResponse = new ExpenseList_Response();
    BaseResponse response = new BaseResponse();

    public VehicleExpense_Controller(Vehicle_Expense_List expenseList) {
        this.expenseList = expenseList;
    }

    @GetMapping("{deviceType}/expense/list")
    public ResponseEntity<?> getExpenseList(@PathVariable("deviceType") Integer deviceType){
        listResponse.expenseList = expenseList.getExpenseList();
        response.apiName = "expense list";
        response.data = listResponse;
        response.status = true;
        response.errorMsg=null;
        return ResponseEntity.ok(response);
    }
}
