package nex.vts.backend.repoImpl;

import nex.vts.backend.exceptions.AppCommonException;
import nex.vts.backend.models.responses.GetExpansesModel;
import nex.vts.backend.repositories.GetExpenseHeaderRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.TransientDataAccessException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class GetExpensesImpl implements GetExpenseHeaderRepo {


    private final short API_VERSION = 1;

    private final Logger logger = LoggerFactory.getLogger(GetExpensesImpl.class);



    private final JdbcTemplate jdbcTemplate;

    public GetExpensesImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public Optional<ArrayList<GetExpansesModel>> findAllExpenses(String date_from, String date_to, Integer vehicleId,Integer deviceType) {

        String sql=" SELECT DATE_TIME                         EXPENSE_DATE,\n" +
                "         AMOUNT                            EXPENSE_AMOUNT,\n" +
                "         expense_unit,\n" +
                "         expense_unit_price,\n" +
                "         GPSNEXGP.get_expense_name (EXPENSE_ID)     EXPENSE_HEADER,\n" +
                "         GPSNEXGP.get_vehicle_name (0, USER_ID)     VEHICLE_NAME,\n" +
                "         description                       expense_notes\n" +
                "    FROM GPSNEXGP.NEX_ALL_EXPENDITURE\n" +
                "   WHERE USER_ID = "+vehicleId+" AND DATE_TIME BETWEEN ' "+date_from+" ' AND '"+date_to +"' ORDER BY DATE_TIME ASC";

        Optional<ArrayList<GetExpansesModel>> getExpenseList=Optional.empty();

        try {

            getExpenseList = Optional.of((ArrayList<GetExpansesModel>) jdbcTemplate.query(sql,
                    BeanPropertyRowMapper.newInstance(GetExpansesModel.class)));
        }
        catch (BadSqlGrammarException e) {
            logger.trace("No Data found with vehicleId is {}  Sql Grammar Exception", vehicleId);
            throw new AppCommonException(4001 + "##Sql Grammar Exception" + deviceType + "##" + API_VERSION);
        }catch (TransientDataAccessException f){
            logger.trace("No Data found with vehicleId is {} network or driver issue or db is temporarily unavailable  ", vehicleId);
            throw new AppCommonException(4002 + "##Network or driver issue or db is temporarily unavailable" + deviceType + "##" + API_VERSION);
        }catch (CannotGetJdbcConnectionException g){
            logger.trace("No Data found with vehicleId is {} could not acquire a jdbc connection  ", vehicleId);
            throw new AppCommonException(4003 + "##A database connection could not be obtained" + deviceType + "##" + API_VERSION);
        }


        if (getExpenseList.get().isEmpty())
        {
            return Optional.empty();
        }
        else {
            return getExpenseList;
        }
    }
}
