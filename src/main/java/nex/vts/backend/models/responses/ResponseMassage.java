package nex.vts.backend.models.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseMassage{

	@JsonProperty("isVehicle Setting Modify")
	private boolean isVehicleSettingModify;

	@JsonProperty("message")
	private String message;
}