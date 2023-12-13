package nex.vts.backend.models.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class ExpenseReportDataList {

    private  String vehicleName,dateTime,engin;
    private LocationInfo locationInfo;
}
