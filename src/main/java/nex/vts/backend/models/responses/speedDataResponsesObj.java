package nex.vts.backend.models.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.Optional;

@Data
public class speedDataResponsesObj {

    @JsonProperty("speedDataResponses")
    private Optional<ArrayList<SpeedDataResponse>> speedDataResponses;
}
