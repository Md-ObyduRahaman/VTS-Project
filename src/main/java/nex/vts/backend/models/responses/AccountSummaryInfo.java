package nex.vts.backend.models.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
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
  @JsonProperty("RunningVehicle")
  private Integer RUNNING_NOW;
  @JsonProperty("todayRunningVehicle")
  private Integer TODAY_RUNNING;
  @JsonProperty("StoppedVehicle")
  private Integer STOP_NOW;
  @JsonProperty("availableSMS")
  private Integer AVAILABLE_SMS;
  @JsonProperty("totalVehicle")
  private Integer TOTAL_VEHICLE;
  @JsonProperty("todayDistance")
  private Integer TODAYS_DISTANCE;
  @JsonProperty("todayAlert")
  private Integer TODAYS_ALERT;
  @JsonProperty("todaySpeedAlert")
  private Integer TODAYS_SPEED_ALERT;
  @JsonProperty("driverScore")
  private Integer DRIVERSCORE;

  private String full_NAME;
  @JsonProperty("billingInfo")
  private DetailsOfClientDue detailsOfClientDue;
  @Value("${consultationDriver.type:0}")
  private  int consultationDriver;
  private  String motherAccountName;
  @Value("${overSpeed.type:0}")
  private  int overSpeed;
}
