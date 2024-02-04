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

    @JsonProperty("runningVehicle")
    private Integer RUNNING_NOW;

    @JsonProperty("todayRunningVehicle")
    private Integer TODAY_RUNNING;

    @JsonProperty("stoppedVehicle")
    private Integer STOP_NOW;

    @JsonProperty("availableSMS")
    private Integer AVAILABLE_SMS;

    @JsonProperty("totalVehicle")
    private Integer TOTAL_VEHICLE;

    @JsonProperty("todayDistance")
    private Integer TODAYS_DISTANCE;

    @JsonProperty("todayAlert")
    private Integer TODAYS_ALERT;

    @JsonProperty("speedViolation")
    private Integer TODAYS_SPEED_ALERT;  // Todays_Speed_Violation

    @JsonProperty("consultationDriver")
    private Integer DRIVERSCORE;

    @JsonProperty("customerName")
    private String full_NAME;

    @JsonProperty("billingInfo")
    private DetailsOfClientDue detailsOfClientDue;

    private String motherAccountName;

}
