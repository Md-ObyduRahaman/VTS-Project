package nex.vts.backend.repositories;



import nex.vts.backend.models.responses.UserFullName;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;


public interface AccountSummaryRepo {

    public Optional<ArrayList<UserFullName>> getUserFullName(Integer profileId, Integer userType, Integer deviceType);

    public double getVehicleData(String p_info_type,String columnName,Integer profileType,Integer profileId,Integer parentId,String dateFrom,String dateTo,Integer deviceType,String packageName) ;


}
