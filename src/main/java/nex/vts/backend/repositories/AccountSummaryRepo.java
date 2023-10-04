package nex.vts.backend.repositories;



import nex.vts.backend.models.responses.AccountSummary;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;


public interface AccountSummaryRepo {

    public Optional<ArrayList<AccountSummary>> getAccountSummary(Integer profileId,Integer userType);
}
