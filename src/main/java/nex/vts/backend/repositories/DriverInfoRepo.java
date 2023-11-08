package nex.vts.backend.repositories;

import nex.vts.backend.models.responses.DriverInfoModel;
import nex.vts.backend.models.responses.GetExpansesModel;

import java.util.ArrayList;
import java.util.Optional;

public interface DriverInfoRepo
{
    Optional<DriverInfoModel> findDriverInfo(Integer id);
}
