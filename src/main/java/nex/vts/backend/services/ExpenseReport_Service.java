package nex.vts.backend.services;

import nex.vts.backend.exceptions.AppCommonException;
import nex.vts.backend.models.responses.DetailsOfExpense;
import nex.vts.backend.models.responses.ExpenseReportResponse;
import nex.vts.backend.models.responses.Response;
import nex.vts.backend.repositories.ExpenseReport_Repo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseReport_Service {
    Logger logger = LoggerFactory.getLogger(ExpenseReport_Service.class);
    private final ExpenseReport_Repo reportRepo;
    Response response = new Response();
    ExpenseReportResponse expenseReportResponse = new ExpenseReportResponse();

    public ExpenseReport_Service(ExpenseReport_Repo reportRepo) {
        this.reportRepo = reportRepo;
    }

    public ExpenseReportResponse getExpenseReport(String groupId,String userId,String fromDate,String toDate,String schemaName){

        int totalSum;

        try {

            List<DetailsOfExpense> detailsOfExpense = (List<DetailsOfExpense>) reportRepo.getExpenseInfo(groupId, userId, fromDate, toDate, schemaName);
            totalSum = detailsOfExpense.stream().mapToInt(DetailsOfExpense::getExpAmount).sum();
            response.setDateTime(fromDate.concat("/ ").concat(toDate));
            response.setTotalExpense(totalSum);
            response.setDetailsOfExpense(detailsOfExpense);

        }
        catch (Exception e){

            logger.error(e.getMessage());
            throw new AppCommonException("");
        }

        expenseReportResponse.setResponse(response);

    return expenseReportResponse;

    }

}
