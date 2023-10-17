package nex.vts.backend.models.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MonthTravleDistanceForAll {

    private Double average, total, max, min;
    private Integer totalCount;
    @JsonProperty("response")
    private List<MonthTravleDistance> monthTravleDistancesList;
}