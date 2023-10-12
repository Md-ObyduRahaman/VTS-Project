package nex.vts.backend.repositories;

import java.util.List;

public interface Vehicle_History_Repo {

    List<Object> getVehicleHistory(Integer vehicleId,String fromDate,String toDate);

}
