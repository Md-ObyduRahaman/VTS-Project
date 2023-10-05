package nex.vts.backend.repositories;

import nex.vts.backend.models.responses.GetExpense;
import nex.vts.backend.models.responses.VehicleList;

import java.util.List;

public interface GetExpenseRepo {

    List<GetExpense> findAllExpenses(String vehicle_id, String date_from, String Date_to);



}
