package nex.vts.backend.models.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VehicleLocationResponse {

/*	@JsonProperty("response")
	public VehicleLocationResponse vehicleLocationResponse;*/

/*	@JsonProperty("code")
	public int code;*/

	@JsonProperty("vehicleLocation")
	public VehicleLocation vehicleLocation;

/*	public VehicleLocationResponse getResponse(){
		return vehicleLocationResponse;
	}*/

/*	public int getCode(){
		return code;
	}*/

	public VehicleLocation getVehicleLocation(){
		return vehicleLocation;
	}
}