package nex.vts.backend.Repository;

import nex.vts.backend.Model.Response.DriverDetails;
import org.springframework.data.repository.CrudRepository;

public interface DriverRepo extends CrudRepository<DriverDetails,Long> {
}
