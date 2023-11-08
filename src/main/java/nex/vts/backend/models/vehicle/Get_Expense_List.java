package nex.vts.backend.models.vehicle;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Get_Expense_List {
    private Integer expenseId;
    private String expenseName;
}
