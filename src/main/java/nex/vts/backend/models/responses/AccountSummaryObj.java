package nex.vts.backend.models.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.Optional;

@Data
public class AccountSummaryObj {

    @JsonProperty("accountSummary")
    private AccountSummaryInfo accountSummaries;

}
