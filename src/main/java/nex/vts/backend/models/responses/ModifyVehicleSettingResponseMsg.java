package nex.vts.backend.models.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ModifyVehicleSettingResponseMsg{

	@JsonProperty("Response Massage")
	private ResponseMassage responseMassage;
}