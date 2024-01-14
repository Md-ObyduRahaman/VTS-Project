package nex.vts.backend.models.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VehicleConfigResponse{

	@JsonProperty("Vehicle Setting Response")
	private String vehicleSettingResponse;
}