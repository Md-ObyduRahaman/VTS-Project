package nex.vts.backend.models.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nex.vts.backend.models.responses.LocationInfo;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class ExpenseReportReqData {

    private  String vehicleName,locationInfo,fromdate, todate;
}
