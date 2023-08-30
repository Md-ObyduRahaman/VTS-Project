package nex.vts.backend.Model.Response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class DriverDetails {

	@JsonProperty("driverId")
	private Long driverId;

	@JsonProperty("Father Name")
	private String fatherName;

	@JsonProperty("Mobile Number")
	private String mobileNumber;

	@JsonProperty("License Number")
	private String licenseNumber;

	@JsonProperty("Address")
	private String address;

	@JsonProperty("Name")
	private String name;

	public void setFatherName(String fatherName){
		this.fatherName = fatherName;
	}

	public void setMobileNumber(String mobileNumber){
		this.mobileNumber = mobileNumber;
	}

	public void setLicenseNumber(String licenseNumber){
		this.licenseNumber = licenseNumber;
	}

	public void setAddress(String address){
		this.address = address;
	}

	public void setName(String name){
		this.name = name;
	}
}