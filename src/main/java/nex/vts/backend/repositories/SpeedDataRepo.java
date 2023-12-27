package nex.vts.backend.repositories;



import nex.vts.backend.dbentities.VTS_LOGIN_USER;
import nex.vts.backend.models.responses.SpeedDataReport;
import nex.vts.backend.models.responses.SpeedDataResponse;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Optional;

public interface SpeedDataRepo {

    public Optional<ArrayList<SpeedDataResponse>> getSpeedDataForhr(String finalToTime, String finalFromTime,Integer vehicleId,Integer deviceType);
    public Optional<ArrayList<SpeedDataResponse>> getSpeedDataForgr(String finalToTime, String finalFromTime,Integer vehicleId,Integer deviceType);

}
