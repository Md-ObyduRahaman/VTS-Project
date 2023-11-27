package nex.vts.backend.models.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountSummaryInfo {
  private Integer todayRunningVehicle,StoppedVehicle,RunningVehicle,availableSMS,todayAlert,totalVehicle;
  private double todayDistance;
  private String full_NAME;
  private DetailsOfClientDue detailsOfClientDue;
}
