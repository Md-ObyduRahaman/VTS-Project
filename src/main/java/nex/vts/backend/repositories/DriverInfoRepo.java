package nex.vts.backend.repositories;

import nex.vts.backend.models.responses.GetExpansesModel;

import java.util.ArrayList;
import java.util.Optional;

public interface DriverInfoRepo
{
    public Optional<ArrayList<GetExpansesModel>> findDriverInfo(String id);
}
