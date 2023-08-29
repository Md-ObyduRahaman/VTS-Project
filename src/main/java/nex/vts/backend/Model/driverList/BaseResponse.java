package nex.vts.backend.Model.driverList;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BaseResponse {

	@JsonProperty("data")
	private Data data;

	@JsonProperty("errorCode")
	private int errorCode;

	@JsonProperty("status")
	private boolean status;

	@JsonProperty("errorMsg")
	private String errorMsg;

	public void setData(Data data){
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