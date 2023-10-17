package nex.vts.backend.repositories;

import javax.sql.DataSource;
import java.util.List;

public interface Vehicle_History_Repo {
    List<Object> getVehicleHistory(Integer vehicleId,String fromDate,String toDate);
    void setDataSource(DataSource dataSource);

}
