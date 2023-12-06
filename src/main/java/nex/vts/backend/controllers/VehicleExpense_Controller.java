//package nex.vts.backend.controllers;
//
//import nex.vts.backend.models.responses.BaseResponse;
//import nex.vts.backend.models.vehicle.ExpenseList_Response;
//import nex.vts.backend.services.Vehicle_Expense_List;
//import nex.vts.backend.utilities.AESEncryptionDecryption;
//import org.springframework.core.env.Environment;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import static nex.vts.backend.utilities.UtilityMethods.deObfuscateId;
//
//@RestController
//@RequestMapping("/api/private/v1")
//public class VehicleExpense_Controller {
//    private final Vehicle_Expense_List expenseList;
//    ExpenseList_Response listResponse = new ExpenseList_Response();
//    BaseResponse response = new BaseResponse();
//    Environment environment;
//    private final short API_VERSION = 1;
//
//    public VehicleExpense_Controller(Vehicle_Expense_List expenseList,Environment environment) {
//        this.expenseList = expenseList;
//        this.environment = environment;
//    }
//
//    @GetMapping("{userId}/{deviceType}/expense/list")
//    public ResponseEntity<?> getExpenseList(@PathVariable("deviceType") Integer deviceType,@PathVariable(value = "userId")Long userId){
////        String activeProfile = environment.getProperty("spring.profiles.active");
////        AESEncryptionDecryption decryptedValue= new AESEncryptionDecryption(activeProfile,deviceType,API_VERSION);
//        Long getUserId = deObfuscateId(userId);
//        listResponse.expenseList = expenseList.getExpenseList();
//        response.apiName = "expense list";
//        response.data = listResponse;
//        response.status = true;
//        response.errorMsg=null;
//        return ResponseEntity.ok(response);
//    }
//}
