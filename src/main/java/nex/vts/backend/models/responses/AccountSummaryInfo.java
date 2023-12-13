package nex.vts.backend.models.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountSummaryInfo {
  @Value("${todayRunningVehicle.type:0}")
  private int todayRunningVehicleType;
  private Integer StoppedVehicle,RunningVehicle,availableSMS,todayAlert,totalVehicle;
  private double todayDistance;
  private String full_NAME;
  private DetailsOfClientDue detailsOfClientDue;
  @Value("${consultationDriver.type:0}")
  private  int consultationDriver;
  private  String motherAccountName;
  @Value("${overSpeed.type:0}")
  private  int overSpeed;
}
