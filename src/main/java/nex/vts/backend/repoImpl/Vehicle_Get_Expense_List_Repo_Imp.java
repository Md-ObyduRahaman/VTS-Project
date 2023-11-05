package nex.vts.backend.repoImpl;

import nex.vts.backend.models.vehicle.Get_Expense_List;
import nex.vts.backend.repositories.Vehicle_Expense_List_Repo;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Repository
public class Vehicle_Get_Expense_List_Repo_Imp implements Vehicle_Expense_List_Repo {

    private final JdbcTemplate jdbcTemplate;

    public Vehicle_Get_Expense_List_Repo_Imp(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Object> getExpenseList() {
        String query = "select ID, EXP_NAME FROM GPSNEXGP.nex_expense_name a where a.READ_ONLY = 1 order by EXP_NAME asc";
        Object expenseList = jdbcTemplate.query(query, new RowMapper<Get_Expense_List>() {
            @Override
            public Get_Expense_List mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Get_Expense_List(
                        rs.getInt("ID"),
                        rs.getString("EXP_NAME")
                );
            }
        });

        return Optional.of(expenseList);
    }
}
