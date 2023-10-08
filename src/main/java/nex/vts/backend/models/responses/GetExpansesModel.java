package nex.vts.backend.models.responses;

import lombok.Data;

@Data
public class GetExpansesModel {

    private String EXPENSE_DATE, EXPENSE_HEADER, VEHICLE_NAME, EXPENSE_NOTES;
    private Integer EXPENSE_AMOUNT,EXPENSE_UNIT,EXPENSE_UNIT_PRICE;
}
