package nex.vts.backend.Model.driverList;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DriverListItem{

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