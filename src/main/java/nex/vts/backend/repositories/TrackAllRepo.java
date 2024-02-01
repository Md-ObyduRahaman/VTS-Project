package nex.vts.backend.repositories;

import nex.vts.backend.models.responses.OverSpeedData;
import nex.vts.backend.models.responses.TrackAllInfo;

import java.util.ArrayList;
import java.util.Optional;

public interface TrackAllRepo {


    Optional<ArrayList<TrackAllInfo>> getOverSpeedInfo(int userType,Long userId, Long p_userId, int deviceType, int apiVersion,String vehicleId);

}
