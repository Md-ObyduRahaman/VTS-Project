package nex.vts.backend.models.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@NoArgsConstructor
public class OverSpeedData {

    @JsonProperty("numberOfCount")
    private Integer totalCount;
    @JsonProperty("sumOfSpeed")
    private double totalSpeed;
    private String alertType;
    @JsonProperty("reportDetails")
    private ArrayList<SpeedReportDetails> speedReportDetails;
}
