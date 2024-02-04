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
    public Object getExpenseInfo(String groupId, String userId, String fromDate, String toDate,String schemaName) {

        String query = "select ROWNUM                          ROWNO," +
                "       EXPENSE_ID                      EXPENSE_ID," +
                "       EXPENSE_AMOUNT                  EXPENSE_AMOUNT," +
                "       EXPENSE_UNIT                    EXPENSE_UNIT, "
                        .concat(schemaName).concat("get_expense_name(EXPENSE_ID) as EXPENSE_NAME" +
                                "from (select EXPENSE_ID," +
                                "             sum(AMOUNT)               as EXPENSE_AMOUNT," +
                                "             NVL(SUM(EXPENSE_UNIT), 0) as EXPENSE_UNIT" +
                                "      from ")
                        .concat(schemaName).concat("NEX_ALL_EXPENDITURE" +
                                "      where GROUPID = ?" +
                                "        and USER_ID = ?" +
                                "        and DATE_TIME between (?) and (?)" +
                                "      group by EXPENSE_ID)" +
                                "order by EXPENSE_NAME asc");

        try {

            return jdbcTemplate.query(query, new Object[]{groupId, userId, fromDate, toDate},
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
