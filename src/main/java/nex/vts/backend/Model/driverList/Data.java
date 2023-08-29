package nex.vts.backend.Model.driverList;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Data{

	@JsonProperty("driverList")
	private List<DriverListItem> driverList;

	public void setDriverList(List<DriverListItem> driverList){
		this.driverList = driverList;
	}
}