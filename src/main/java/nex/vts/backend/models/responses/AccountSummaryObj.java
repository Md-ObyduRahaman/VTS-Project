package nex.vts.backend.models.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Optional;

public class AccountSummaryObj {

    @JsonProperty("accountSummarieList")
    private Optional<ArrayList<AccountSummary>> accountSummaries;

    public void setAccountSummaries(Optional<ArrayList<AccountSummary>> accountSummaries) {
        this.accountSummaries = accountSummaries;
    }
}
