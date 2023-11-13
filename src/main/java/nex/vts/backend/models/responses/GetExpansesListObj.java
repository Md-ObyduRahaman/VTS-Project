package nex.vts.backend.models.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Optional;

@Setter
public class GetExpansesListObj
{

    @JsonProperty("getExpansesData")
    private Optional<ArrayList<GetExpansesModel>> getExpansesModels;


}
