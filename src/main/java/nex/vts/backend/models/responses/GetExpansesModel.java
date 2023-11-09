package nex.vts.backend.models.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GetExpansesModel
{

    @JsonProperty("expense_date")
    private String EXPENSE_DATE;
    @JsonProperty("expense_header")
    private String EXPENSE_HEADER;
    @JsonProperty("vehicle_name")
    private String VEHICLE_NAME;
    @JsonProperty("expense_notes")
    private String EXPENSE_NOTES;
    @JsonProperty("expense_amount")
    private Integer EXPENSE_AMOUNT;
    @JsonProperty("expense_unit")
    private Integer EXPENSE_UNIT;
    @JsonProperty("expense_unit_price")
    private Integer EXPENSE_UNIT_PRICE;

}
