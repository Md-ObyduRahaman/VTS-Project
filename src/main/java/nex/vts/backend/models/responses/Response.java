package nex.vts.backend.models.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonPropertyOrder({"dateTime","totalExpense","detailsOfExpense"})
public class Response{

	@JsonProperty("dateTime")
	private String dateTime;

	@JsonProperty("detailsOfExpense")
	private List<DetailsOfExpense> detailsOfExpense;

	@JsonProperty("totalExpense")
	private int totalExpense;
}