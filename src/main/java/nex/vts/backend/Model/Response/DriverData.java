package nex.vts.backend.Model.Response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DriverData {

	@JsonProperty("driverDetails")
	private DriverDetails driverDetails;

	public void setDriverList(DriverDetails driverDetails){
		this.driverDetails = driverDetails;
	}
}