package nex.vts.backend.repoImpl;

import nex.vts.backend.exceptions.AppCommonException;
import nex.vts.backend.repositories.AddExpense_Repo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Map;

@Repository
public class AddExpense_Imp implements AddExpense_Repo {

    private static Logger logger = LoggerFactory.getLogger(AddExpense_Imp.class);
    private JdbcTemplate jdbcTemplate;
    private DataSource dataSource;

    SqlParameterSource parameterSource = new MapSqlParameterSource();

    @Autowired
    public AddExpense_Imp(DataSource dataSource,JdbcTemplate jdbcTemplate) {
        this.dataSource = dataSource;
        this.jdbcTemplate = jdbcTemplate;
    }


/*    @Override
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

    }*/

    @Override
    public String addExpenseForGpAndM2M(String oparationType,
                                     Integer profileType,
                                     Integer profileId,
                                     Integer parentProfileId,
                                     String  vehicleId,
                                     Integer expenseId,
                                     String dateTime,
                                     String amount,
                                     String expenseDescription,
                                     String expenseRowId,
                                     Integer expenseUnit,
                                     Integer expenseUnitPrice,
                                     String schemaName) throws SQLException {

/*        String response = new String();

        SimpleJdbcCall  jdbcCall = new SimpleJdbcCall(dataSource).withProcedureName("manage_expense");

        parameterSource = new MapSqlParameterSource()
                .addValue("p_operation_type",oparationType)
                .addValue("p_profile_type",profileType)
                .addValue("p_profile_id",profileId)
                .addValue("p_parent_profile_id",parentProfileId)
                .addValue("p_vehice_id",vehicleId)
                .addValue("p_expense_id",expenseId)
                .addValue("p_date_time",dateTime)
                .addValue("p_amount",amount)
                .addValue("p_expense_desc",expenseDescription)
                .addValue("p_expense_rowid",expenseRowId)
                .addValue("p_response",response)
                .addValue("p_expense_unit",expenseUnit)
                .addValue("p_expense_unit_price",expenseUnitPrice);

        Map<String, Object> outParam = jdbcCall.execute(parameterSource);*/

        String out = null;
        Connection connection = dataSource.getConnection();

        CallableStatement statement = connection
                .prepareCall("{call manage_expense(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");

        statement.setString(1,oparationType);
        statement.setInt(2,profileType);
        statement.setInt(3,profileId);
        statement.setInt(4,parentProfileId);
        statement.setString(5,vehicleId);
        statement.setInt(6,expenseId);
        statement.setString(7,dateTime);
        statement.setString(8,amount);
        statement.setString(9,expenseDescription);
        statement.setString(10,expenseRowId);
        statement.registerOutParameter(11,Types.VARCHAR);
        statement.setInt(12,expenseUnit);
        statement.setInt(13,expenseUnitPrice);

        try {

            boolean result = statement.execute();
            out =    statement.getString(11);

            if (result){
                ResultSet resultSet = statement.getResultSet();
            }
        }catch (Exception e){

            e.getMessage();
        }finally {

            statement.close();
            connection.close();
        }

        return out;
    }
}
