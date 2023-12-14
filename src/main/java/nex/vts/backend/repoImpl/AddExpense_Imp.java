package nex.vts.backend.repoImpl;

import nex.vts.backend.exceptions.AppCommonException;
import nex.vts.backend.repositories.AddExpense_Repo;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Types;

@Repository
public class AddExpense_Imp implements AddExpense_Repo {

    private JdbcTemplate jdbcTemplate;

    public AddExpense_Imp(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Object addExpense(String userId, String groupId, String expenseId, String dateTime, String amount, String schemaName, String description, Integer expenseId2, Integer deptId) {
        System.out.println(userId);
        System.out.println(groupId);
        System.out.println(expenseId);
        System.out.println(dateTime);
        System.out.println(amount);
        System.out.println(deptId);
        try {
            String query = "INSERT INTO ".concat(schemaName).concat("nex_all_expenditure (USER_ID, GROUPID,\n" +
                    "                                 EXPENSE_ID, DATE_TIME, AMOUNT, DESCRIPTION, EXPENSE_ID_N,\n" +
                    "                                 DEPT_ID)\n" +
                    "VALUES (?,?,?,?,?,?,?,?)");
            Object[] param = new Object[]{userId, groupId, expenseId, dateTime, amount, description, expenseId2, deptId};
            int[] types = new int[]{Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.INTEGER, Types.INTEGER};
            int flag = jdbcTemplate.update(query, param, types);
            return flag;
        } catch (Exception e) {
            throw new AppCommonException(e.getMessage());
        }

    }
}
