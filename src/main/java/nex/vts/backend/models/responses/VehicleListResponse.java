package nex.vts.backend.models.responses;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class VehicleListResponse {

	@JsonProperty("totalVehicle")
	public int totalVehicle;

	@JsonProperty("code")
	public int code;

	@JsonProperty("fetchedVehicle")
	public int fetchedVehicle;

	@JsonProperty("vehicles")
	public List<VehiclesItem> vehicles;

}