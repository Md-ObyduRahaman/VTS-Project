package nex.vts.backend.models.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

@Getter
@Setter
public class TrackNowResponse{

	@JsonProperty("vehicle current location")
	private Optional<VehicleCurrentLocation> vehicleCurrentLocation;
}