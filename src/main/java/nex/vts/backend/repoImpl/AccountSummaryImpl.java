package nex.vts.backend.repoImpl;

import nex.vts.backend.exceptions.AppCommonException;
import nex.vts.backend.models.responses.AccountSummary;
import nex.vts.backend.models.responses.FavouriteVehiclelModel;
import nex.vts.backend.repositories.AccountSummaryRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.TransientDataAccessException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class AccountSummaryImpl implements AccountSummaryRepo {

    private final Logger logger = LoggerFactory.getLogger(AccountSummaryImpl.class);


    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Optional<ArrayList<AccountSummary>> getAccountSummary(Integer profileId,Integer userType) {

         String sql = null;

        switch (userType) {
            case 1:
                //profileId=1
                sql = "SELECT NCC.COMPANY_NAME FULL_NAME, NCC.CONTACT_NAME\n" +
                        "  FROM NEX_CORPORATE_CLIENT NCC\n" +
                        " WHERE NCC.ID = " + profileId;

                break;
            case 2:
                //profileId=29
                sql = "SELECT NVD.DEPT_NAME FULL_NAME, NVD.CONTACT_NAME\n" +
                        "  FROM GPSNEXGP.NEX_VEHICLE_DEPT NVD\n" +
                        " WHERE NVD.ID = "+profileId;
                break;
            case 3:
                //profileId=127
                sql = "SELECT NIC.FULL_NAME, NCC.CONTACT_NAME\n" +
                        "  FROM NEX_INDIVIDUAL_CLIENT  NIC\n" +
                        "       JOIN NEX_CORPORATE_CLIENT NCC ON NCC.ID = NIC.COMPANY_ID\n" +
                        " WHERE NIC.ID = "+profileId;
                break;

            case 4:
                //profileId=127
                sql = "SELECT NIC.FULL_NAME, NCC.CONTACT_NAME\n" +
                        "  FROM NEX_INDIVIDUAL_CLIENT  NIC\n" +
                        "       JOIN NEX_CORPORATE_CLIENT NCC ON NCC.ID = NIC.COMPANY_ID\n" +
                        " WHERE NIC.ID ="+profileId;
                break;
            default:
                throw new AppCommonException(8001 + "##UserType is Wrong");
        }


        Optional<ArrayList<AccountSummary>> accountSummaries = Optional.empty();

        try {

            accountSummaries = Optional.of((ArrayList<AccountSummary>) jdbcTemplate.query(sql,
                    BeanPropertyRowMapper.newInstance(AccountSummary.class)));
        } catch (BadSqlGrammarException e) {
            logger.trace("No Data found with profileId is {}  Sql Grammar Exception", profileId);
            throw new AppCommonException(4001 + "##Sql Grammar Exception");
        } catch (TransientDataAccessException f) {
            logger.trace("No Data found with profileId is {} network or driver issue or db is temporarily unavailable  ", profileId);
            throw new AppCommonException(4002 + "##Network or driver issue or db is temporarily unavailable");
        } catch (CannotGetJdbcConnectionException g) {
            logger.trace("No Data found with profileId is {} could not acquire a jdbc connection  ", profileId);
            throw new AppCommonException(4003 + "##A database connection could not be obtained");
        }

        if (accountSummaries.get().isEmpty())
        {
            return Optional.empty();
        }
        else {
            return accountSummaries;
        }




    }


}
