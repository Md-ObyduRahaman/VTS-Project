package nex.vts.backend.Model.Response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class VehiclelistItem{

	@JsonProperty("vehicleName")
	private String vehicleName;

	@JsonProperty("color")
	private String color;

	@JsonProperty("registrationNumber")
	private String registrationNumber;

	@JsonProperty("engineStatus")
	private String engineStatus;

	@JsonProperty("model")
	private String model;


	public void setVehicleName(String vehicleName){
		this.vehicleName = vehicleName;
	}

	public void setColor(String color){
		this.color = color;
	}

	public void setRegistrationNumber(String registrationNumber){
		this.registrationNumber = registrationNumber;
	}

	public void setEngineStatus(String engineStatus){
		this.engineStatus = engineStatus;
	}

	public void setModel(String model){
		this.model = model;
	}
}