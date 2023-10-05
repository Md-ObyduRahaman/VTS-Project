package nex.vts.backend.repoImpl;

import nex.vts.backend.models.responses.GetExpense;
import nex.vts.backend.models.responses.VehicleList;
import nex.vts.backend.repositories.GetExpenseRepo;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Repository
public class GetExpensesImpl implements GetExpenseRepo {


    private final JdbcTemplate jdbcTemplate;

    public GetExpensesImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<GetExpense> findAllExpenses(String vehicle_id, String date_from, String Date_to) {
        return null;
    }

}
