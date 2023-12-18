package nex.vts.backend.models.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonPropertyOrder({"maxSpeed", "rowNo", "ids", "vehicleId", "groupId", "deviceId", "timeStamp", "latitude", "longitude", "timeInNumber", "position", "speed"})
public class HistoriesItem{

	@JsonProperty("rowNo")
	private int rowNo;

	@JsonProperty("maxSpeed")
	private String maxSpeed;

	@JsonProperty("longitude")
	private Double longitude;

	@JsonProperty("groupId")
	private String groupId;

	@JsonProperty("latitude")
	private Double latitude;

	@JsonProperty("timeInNumber")
	private Long timeInNumber;

	@JsonProperty("id")
	private Long id;

	@JsonProperty("vehicleId")
	private String vehicleId;

	@JsonProperty("timeStamp")
	private String time;

	@JsonProperty("position")
	private String position;

	@JsonProperty("speed")
	private String speed;

	@JsonProperty("deviceId")
	private String deviceId;

	public HistoriesItem(
			String maxSpeed,
			int rowNo,
			Long id,
			String vehicleId,
			String groupId,
			String deviceId,
			String time,
			Double latitude,
			Double longitude,
			Long timeInNumber,
			String position,
			String speed
						 ) {

		this.rowNo = rowNo;
		this.longitude = longitude;
		this.groupId = groupId;
		this.latitude = latitude;
		this.timeInNumber = timeInNumber;
		this.id = id;
		this.vehicleId = vehicleId;
		this.time = time;
		this.position = position;
		this.speed = speed;
		this.deviceId = deviceId;
		this.maxSpeed = maxSpeed;
	}
}