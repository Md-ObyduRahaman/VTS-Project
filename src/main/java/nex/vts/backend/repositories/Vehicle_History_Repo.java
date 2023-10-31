package nex.vts.backend.repositories;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

public interface Vehicle_History_Repo {
   Optional<Object> getVehicleHistory(Integer vehicleId, String fromDate, String toDate);
    void setDataSource(DataSource dataSource);

}
