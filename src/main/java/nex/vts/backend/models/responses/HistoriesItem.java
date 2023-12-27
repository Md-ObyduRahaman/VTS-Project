package nex.vts.backend.models.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonPropertyOrder({/*"maxSpeed",*/ "rowNo", "ids", "vehicleId", "groupId", "deviceId", "timeStamp", "latitude", "longitude", "timeInNumber", "position", "speed"})
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class HistoriesItem {

/*    @JsonProperty("maxSpeed")
    private String maxSpeed;*/

    @JsonProperty("rowNo")
    private int rowNo;

    @JsonProperty("id")
    private Long id;

    @JsonProperty("vehicleId")
    private String vehicleId;

    @JsonProperty("groupId")
    private String groupId;

    @JsonProperty("deviceId")
    private String deviceId;

    @JsonProperty("timeStamp")
    private String time;

    @JsonProperty("latitude")
    private Double latitude;

    @JsonProperty("longitude")
    private Double longitude;

    @JsonProperty("timeInNumber")
    private Long timeInNumber;

    @JsonProperty("position")
    private String position;

    @JsonProperty("speed")
    private String speed;

}