package nex.vts.backend.models.responses;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class GetExpense {

    private  String EXPENSE_DATE, EXPENSE_AMOUNT, EXPENSE_UNIT, EXPENSE_UNIT_PRICE, EXPENSE_HEADER, VEHICLE_NAME, EXPENSE_NOTES;
}
