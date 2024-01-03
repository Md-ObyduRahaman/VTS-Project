package nex.vts.backend.repositories;



import nex.vts.backend.models.responses.AccountSummaryInfo;
import nex.vts.backend.models.responses.UserFullName;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;


public interface AccountSummaryRepo {

    public Optional<ArrayList<UserFullName>> getUserFullName(Integer profileId, Integer userType, Integer deviceType);

    public Optional<ArrayList<AccountSummaryInfo>> getVehicleDataforM2m( Integer profileType, Integer profileId, Integer parentId,  Integer deviceType) ;
    public Optional<ArrayList<AccountSummaryInfo>> getVehicleDataforGP( Integer profileType, Integer profileId, Integer parentId,  Integer deviceType) ;


}
