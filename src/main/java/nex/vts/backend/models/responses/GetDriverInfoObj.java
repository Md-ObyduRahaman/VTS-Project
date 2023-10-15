package nex.vts.backend.models.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Optional;

public class GetDriverInfoObj {

    @JsonProperty("GetExpansesData")
    private Optional<ArrayList<GetExpansesModel>> getDriverInfo;
}
