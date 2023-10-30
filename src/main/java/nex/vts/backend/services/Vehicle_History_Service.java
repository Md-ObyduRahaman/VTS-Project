package nex.vts.backend.services;

import nex.vts.backend.repositories.Vehicle_History_Repo;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Vehicle_History_Service {
    private final Vehicle_History_Repo history_repo;

    public Vehicle_History_Service(Vehicle_History_Repo historyRepo) {
        history_repo = historyRepo;
    }

    public Object getVehicleHistory(Integer vehicleId, String fromDate, String toDate) {
        try {
            return history_repo.getVehicleHistory(vehicleId, fromDate, toDate);
        }catch (Exception e){
            return e.getMessage();
        }

    }
}
