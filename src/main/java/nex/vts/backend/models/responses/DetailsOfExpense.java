package nex.vts.backend.models.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class DetailsOfExpense {

	@JsonProperty("serialNo")
	private int serialNo;

	@JsonProperty("expRowId")
	private String expRowId;

	@JsonProperty("expAmount")
	private Integer expAmount;

	@JsonProperty("expUnitData")
	private Integer expUnitData;

	@JsonProperty("expHeader")
	private String expHeader;


}