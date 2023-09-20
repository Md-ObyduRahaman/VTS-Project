package nex.vts.backend.repositories;

import nex.vts.backend.models.responses.DriverDetails;
import org.springframework.data.repository.CrudRepository;

public interface DriverRepo extends CrudRepository<DriverDetails, Long> {
}
