package nex.vts.backend.models.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class ModifyVehicleSettings{

	@JsonProperty("id")
	private int id;

	@JsonProperty("userId")
	private String userId;

	@JsonProperty("cellPhone")
	private String cellPhone;

	@JsonProperty("email")
	private String email;

	@JsonProperty("favourite")
	private int favourite;

	@JsonProperty("maxCarSpeed")
	private String maxCarSpeed;

}