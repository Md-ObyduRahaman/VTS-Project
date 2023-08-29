package nex.vts.backend.Model.vehicleList;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"status","data","errorCode","errorMsg"})
public class BaseResponse{

	@JsonProperty("data")
	private Object data;

	@JsonProperty("errorCode")
	private int errorCode;

	@JsonProperty("status")
	private boolean status;

	@JsonProperty("errorMsg")
	private String errorMsg="";

	public void setData(Object data){
		this.data = data;
	}

	public void setErrorCode(int errorCode){
		this.errorCode = errorCode;
	}

	public void setStatus(boolean status){
		this.status = status;
	}

	public void setErrorMsg(String errorMsg){
		this.errorMsg = errorMsg;
	}
}