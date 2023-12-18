package nex.vts.backend.repositories;

import javax.sql.DataSource;
import java.util.Optional;

public interface VehicleHistoryRepo {
   Optional<Object> getVehicleHistory(Integer vehicleId, String fromDate, String toDate);
/*    void setDataSource(DataSource dataSource);*/
    Object getVehicleHistoryForGpAndM2M(String vehicleId,String fromDateTime,String toDateTime,String schemaName);

}
