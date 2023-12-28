package nex.vts.backend.models.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonPropertyOrder({"code","totalCount","histories"})
public class History {

	@JsonProperty("code")
	private int code;

	@JsonProperty("histories")
	private List<HistoriesItem> histories;

	@JsonProperty("totalCount")
	private int totalCount;
}
