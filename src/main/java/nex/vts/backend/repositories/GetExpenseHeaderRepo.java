package nex.vts.backend.repositories;


import nex.vts.backend.models.responses.GetExpansesModel;

import java.util.ArrayList;
import java.util.Optional;

public interface GetExpenseHeaderRepo {
    public Optional<ArrayList<GetExpansesModel>> findAllExpenses(String date_from,String date_to,Integer vehicleId,Integer deviceType);

}
