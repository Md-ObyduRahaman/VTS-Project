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
    private DataSource dataSource;
    private final short API_VERSION = 1;

    @Autowired
    public AddExpense_Imp(DataSource dataSource) {
        this.dataSource = dataSource;
    }

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
        String out = null;
        Connection connection = dataSource.getConnection();

        String storeProcedure = "{call ".concat(schemaName).concat("manage_expense(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");

        CallableStatement statement = connection.prepareCall(storeProcedure);

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

            logger.error(e.getMessage());
            throw new AppCommonException(600 + "##Required parameter is missing" + profileId + "##" + API_VERSION);

        }finally {

            statement.close();
            connection.close();
        }

        return out;
    }
}
