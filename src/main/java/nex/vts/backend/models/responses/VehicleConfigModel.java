package nex.vts.backend.models.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@JsonPropertyOrder(value = {"id", "userId", "email", "cellPhone", "maxCarSpeed", "isFavourite"})
public class VehicleConfigModel{

	@JsonProperty("id")
	private int id;

	@JsonProperty("userId")
	private String userId;

	@JsonProperty("cellPhone")
	private String cellPhone;

	@JsonProperty("email")
	private String email;

	@JsonProperty("isFavourite")
	private int isFavourite;

	@JsonProperty("maxCarSpeed")
	private String maxCarSpeed;


}