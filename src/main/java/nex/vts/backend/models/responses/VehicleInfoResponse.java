package nex.vts.backend.models.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

@Getter
@Setter
public class VehicleInfoResponse {

	@JsonProperty("vehicleDetails")
	private VehicleDetailInfo vehicleDetailInfo;

	@JsonProperty("massage")
	private String massage;

}