package nex.vts.backend.models.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginResponse{

	@JsonProperty("loginSuccess")
	private boolean loginSuccess;

	@JsonProperty("serverDateTime")
	private String serverDateTime;

	@JsonProperty("token")
	private String token;

	public void setLoginSuccess(boolean loginSuccess){
		this.loginSuccess = loginSuccess;
	}

	public void setServerDateTime(String serverDateTime){
		this.serverDateTime = serverDateTime;
	}

	public void setToken(String token){
		this.token = token;
	}
}
