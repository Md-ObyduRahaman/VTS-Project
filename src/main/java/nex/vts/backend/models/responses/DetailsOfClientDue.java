package nex.vts.backend.models.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetailsOfClientDue {
    private  String currentDueLable,currentDue,lastReceivedDate;
    private Integer lastReceivedAmount,lastBilled;
}
