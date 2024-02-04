package nex.vts.backend.repoImpl;

import nex.vts.backend.exceptions.AppCommonException;
import nex.vts.backend.models.responses.DetailsOfExpense;
import nex.vts.backend.repositories.ExpenseReport_Repo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class ExpenseReportRepo_Imp implements ExpenseReport_Repo {

    private Logger logger = LoggerFactory.getLogger(ExpenseReportRepo_Imp.class);

    private final short API_VERSION = 1;

    private final JdbcTemplate jdbcTemplate;

    public ExpenseReportRepo_Imp(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Object getExpenseInfo(String groupId, String userId, String fromDate, String toDate,String schemaName,Integer offSet,Integer limit) {

        String query = "select ROWNUM                          ROWNO,\n" +
                "       EXPENSE_ID                      EXPENSE_ID,\n" +
                "       EXPENSE_AMOUNT                  EXPENSE_AMOUNT,\n" +
                "       EXPENSE_UNIT                    EXPENSE_UNIT, "
                        .concat(schemaName).concat("get_expense_name(EXPENSE_ID) as EXPENSE_NAME\n" +
                                "from (select EXPENSE_ID,\n" +
                                "             sum(AMOUNT)               as EXPENSE_AMOUNT,\n" +
                                "             NVL(SUM(EXPENSE_UNIT), 0) as EXPENSE_UNIT\n" +
                                "      from ")
                        .concat(schemaName).concat("NEX_ALL_EXPENDITURE\n" +
                                "      where GROUPID = ?\n" +
                                "        and USER_ID = ?\n" +
                                "        and DATE_TIME between (?) and (?)\n" +
                                "      group by EXPENSE_ID)\n" +
                                "order by EXPENSE_NAME asc\n").concat("OFFSET ? ROWS \n" +
                                "FETCH NEXT ? ROWS ONLY");

        try {

            return jdbcTemplate.query(query, new Object[]{groupId, userId, fromDate, toDate, offSet, limit},
                    new RowMapper<DetailsOfExpense>() {
                @Override
                public DetailsOfExpense mapRow(ResultSet rs, int rowNum) throws SQLException {

                    return new DetailsOfExpense(

                            rs.getInt("ROWNO"),
                            rs.getString("EXPENSE_ID"),
                            rs.getInt("EXPENSE_AMOUNT"),
                            rs.getInt("EXPENSE_UNIT"),
                            rs.getString("EXPENSE_NAME")
                    );
                }});
        }
        catch (Exception e){

            logger.error(e.getMessage());
            throw new AppCommonException(212 + "##Required field is missing"+"##" + API_VERSION );

        }
    }
}
