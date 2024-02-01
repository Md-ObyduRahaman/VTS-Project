package nex.vts.backend.models.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
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

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonProperty("histories")
	private List<HistoriesItem> histories;

	@JsonProperty("totalCount")
	private int totalCount;

/*	@JsonProperty("histories")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private List<HistoriesItemTwo> itemTwos;*/
}
