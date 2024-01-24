package nex.vts.backend.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import nex.vts.backend.dbentities.VTS_LOGIN_USER;
import nex.vts.backend.exceptions.AppCommonException;
import nex.vts.backend.models.responses.BaseResponse;
import nex.vts.backend.repoImpl.RepoVtsLoginUser;
import nex.vts.backend.services.ExpenseReport_Service;
import nex.vts.backend.utilities.AESEncryptionDecryption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static nex.vts.backend.utilities.UtilityMethods.deObfuscateId;

import java.util.Optional;

@RestController
@RequestMapping("/api/private/v1")
public class CtrlExpenseReport {
    private final short API_VERSION = 1;

    @Autowired
    RepoVtsLoginUser repoVtsLoginUser;
    BaseResponse response = new BaseResponse();
    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private Environment environment;
    private final ExpenseReport_Service expenseReportService;

    public CtrlExpenseReport(ExpenseReport_Service expenseReportService) {
        this.expenseReportService = expenseReportService;
    }


    @GetMapping("{deviceType}/vehicle/{vehicleId}/report/{fromDate}/{toDate}")
    public ResponseEntity<?> getExpenseReport(@PathVariable(value = "deviceType")Integer deviceType,
                                              @PathVariable(value = "vehicleId")Integer vehicleId,
                                              @PathVariable(value = "fromDate")String fromDate,
                                              @PathVariable(value = "toDate")String toDate
                                              ){

        fromDate = fromDate.substring(0,2).
                   concat("-".concat(fromDate.substring(2,4)))
                .concat("-".concat(fromDate.substring(4)));

        toDate = toDate.substring(0,2)
                .concat("-".concat(toDate.substring(2,4)))
                .concat("-".concat(toDate.substring(4)));

        vehicleId = Math.toIntExact(deObfuscateId(Long.valueOf(vehicleId)));

        String activeProfile = environment.getProperty("spring.profiles.active");
        Integer operatorId = Integer.valueOf(environment.getProperty("application.profiles.operatorid"));
        String schemaName = environment.getProperty("application.profiles.shcemaName");
        AESEncryptionDecryption encryptionDecryption = new AESEncryptionDecryption(activeProfile, deviceType, API_VERSION);

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        VTS_LOGIN_USER loginUser = new VTS_LOGIN_USER();
        Optional<VTS_LOGIN_USER> vtsLoginUserOpt = repoVtsLoginUser.findByUserName(username, environment.getProperty("application.profiles.shcemaName"));

        if (vtsLoginUserOpt.isPresent())
            loginUser = vtsLoginUserOpt.get();
        else
            throw new AppCommonException(400 + "##login cred not found##" + loginUser.getPROFILE_ID() + "##" + API_VERSION);


        response.data = expenseReportService.getExpenseReport(String.valueOf(loginUser.getPROFILE_ID()),
                String.valueOf(vehicleId),fromDate,toDate,schemaName);

        response.status = true;
        response.apiName = "Expense Report";

        return ResponseEntity.ok(response);

    }
}
