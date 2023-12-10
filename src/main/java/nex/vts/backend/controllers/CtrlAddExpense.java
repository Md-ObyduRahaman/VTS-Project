package nex.vts.backend.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nex.vts.backend.dbentities.VTS_LOGIN_USER;
import nex.vts.backend.exceptions.AppCommonException;
import nex.vts.backend.models.responses.BaseResponse;
import nex.vts.backend.models.responses.ExpenseModel;
import nex.vts.backend.repoImpl.RepoVtsLoginUser;
import nex.vts.backend.repositories.AddExpense_Repo;
import nex.vts.backend.services.AddExpense_Service;
import nex.vts.backend.utilities.AESEncryptionDecryption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/private/v1")
public class CtrlAddExpense {

    private final short API_VERSION = 1;
    @Autowired
    RepoVtsLoginUser repoVtsLoginUser;
    BaseResponse response = new BaseResponse();
    @Autowired
    private Environment environment;
    @Autowired
    private AddExpense_Service addExpenseService;

    @PostMapping(value = "/{deviceType}/add/expense", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addExpense(@PathVariable(value = "deviceType") Integer deviceType, @RequestParam(value = "data") String reqBody) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        String activeProfile = environment.getProperty("spring.profiles.active");
        Integer operatorId = Integer.valueOf(environment.getProperty("application.profiles.operatorid"));
        String schemaName = environment.getProperty("application.profiles.shcemaName");
        AESEncryptionDecryption decryptedValue = new AESEncryptionDecryption(activeProfile, 1, API_VERSION);
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        VTS_LOGIN_USER loginUser = new VTS_LOGIN_USER();
        Optional<VTS_LOGIN_USER> vtsLoginUserOpt = repoVtsLoginUser.findByUserName(username, environment.getProperty("application.profiles.shcemaName"));

        if (vtsLoginUserOpt.isPresent()) loginUser = vtsLoginUserOpt.get();
        else
            throw new AppCommonException(400 + "##login cred not found##" + loginUser.getPROFILE_ID() + "##" + API_VERSION);

        ExpenseModel expenseModel = mapper.readValue(reqBody, ExpenseModel.class);

        String groupId = String.valueOf(loginUser.getPROFILE_ID());
        String userId = expenseModel.userId;
        String expenseId = expenseModel.expenseId;
        String date = expenseModel.dateTime;
        String amount = expenseModel.amount;
        String description = expenseModel.description;
        Integer expenseId2 = expenseModel.expenseId2;
        Integer deptId = expenseModel.deptId;

        if (!(groupId.isEmpty() & userId.isEmpty() & expenseId.isEmpty() & date.isEmpty() & amount.isEmpty() & expenseId2.equals(null) & deptId.equals(null))) {
            response.data = addExpenseService.addExpenseService(userId, groupId, expenseId, date, amount, schemaName, description, expenseId2, deptId);
            response.status = true;
            response.apiName = "Add Expense";
        } else {
            response.data = null;
            response.status = false;
            response.apiName = "add Expense";
            response.errorMsg = "Operation did not Successful";
            response.errorCode = 000;
        }
        return ResponseEntity.ok(response);
    }
}
