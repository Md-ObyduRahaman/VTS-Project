package nex.vts.backend.repoImpl;

import nex.vts.backend.exceptions.AppCommonException;
import nex.vts.backend.repositories.AddExpense_Repo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Types;

@Repository
public class AddExpense_Imp implements AddExpense_Repo {

    private static Logger logger = LoggerFactory.getLogger(AddExpense_Imp.class);
    private JdbcTemplate jdbcTemplate;

    public AddExpense_Imp(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int addExpenseForM2M(String vehicleId, String profileId, String expenseId, String dateTime, String amount, String description, Integer expenseId2, Integer deptId, String schemaName) {

        try {
            String query = "INSERT INTO ".concat(schemaName).concat("nex_all_expenditure (USER_ID, GROUPID," +
                    "                                 EXPENSE_ID, DATE_TIME, AMOUNT, DESCRIPTION, EXPENSE_ID_N," +
                    "                                 DEPT_ID)" +
                    "VALUES (?,?,?,?,?,?,?,?)");
            Object[] param = new Object[]{vehicleId, profileId, expenseId, dateTime, amount, description, expenseId2, deptId};
            int[] types = new int[]{Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.INTEGER, Types.INTEGER};
            int flag = jdbcTemplate.update(query, param, types);
            return flag;
        } catch (Exception e) {

            logger.error("An exception occured when execute the insert query",vehicleId,profileId,expenseId);
            throw new AppCommonException(e.getMessage());
        }

    }

    @Override
    public int addExpenseForGp(String vehicleId, String profileId, String expenseId, String dateTime, String amount, String description, Integer expenseId2, Integer deptId,String schemaName) {


        return 0;
    }
}
