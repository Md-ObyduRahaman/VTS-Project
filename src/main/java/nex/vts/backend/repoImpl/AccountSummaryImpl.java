package nex.vts.backend.repoImpl;

import nex.vts.backend.exceptions.AppCommonException;
import nex.vts.backend.models.responses.UserFullName;
import nex.vts.backend.repositories.AccountSummaryRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.TransientDataAccessException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class AccountSummaryImpl implements AccountSummaryRepo {

    private final Logger logger = LoggerFactory.getLogger(AccountSummaryImpl.class);

    private final DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    Environment environment;
    private final short API_VERSION = 1;

    public AccountSummaryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<ArrayList<UserFullName>> getUserFullName(Integer profileId, Integer userType, Integer deviceType) {



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
                throw new AppCommonException(8001 + "##UserType is Wrong##"+deviceType+"##"+API_VERSION);
        }


        Optional<ArrayList<UserFullName>> accountSummaries = Optional.empty();

        try {

            accountSummaries = Optional.of((ArrayList<UserFullName>) jdbcTemplate.query(sql,
                    BeanPropertyRowMapper.newInstance(UserFullName.class)));
        } catch (BadSqlGrammarException e) {
            logger.trace("No Data found with profileId is {}  Sql Grammar Exception", profileId);
            throw new AppCommonException(4001 + "##Sql Grammar Exception"+deviceType+"##"+API_VERSION);
        } catch (TransientDataAccessException f) {
            logger.trace("No Data found with profileId is {} network or driver issue or db is temporarily unavailable  ", profileId);
            throw new AppCommonException(4002 + "##Network or driver issue or db is temporarily unavailable"+deviceType+"##"+API_VERSION);
        } catch (CannotGetJdbcConnectionException g) {
            logger.trace("No Data found with profileId is {} could not acquire a jdbc connection  ", profileId);
            throw new AppCommonException(4003 + "##A database connection could not be obtained"+deviceType+"##"+API_VERSION);
        }

        if (accountSummaries.get().isEmpty())
        {
            return Optional.empty();
        }
        else {
            return accountSummaries;
        }

    }

    @Override
    public double getVehicleData(String p_info_type,String columnName,Integer profileType,Integer profileId,Integer parentId,String dateFrom,String dateTo,Integer deviceType,String packageName) {
         Integer result= 0;
        String sql=null;
        String schemaName=environment.getProperty("application.profiles.shcemaName");

        try {
            if(p_info_type.equals("todayDistance")){
                 sql = "SELECT "+schemaName+packageName+"( ?, ?, ?, ?, ?) AS "+columnName+" FROM DUAL";
            }else {
                p_info_type = "'" + p_info_type + "'";
                 sql = "SELECT "+schemaName+packageName+"("+p_info_type+", ?, ?, ?, ?, ?) AS "+columnName+" FROM DUAL";
            }
            System.out.println(sql);
            Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, profileType);
            statement.setInt(2, profileId);
            statement.setInt(3, parentId);
            statement.setString(4, dateFrom);
            statement.setString(5, dateTo);

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                result= Integer.valueOf(rs.getString(columnName));
            }

        } catch (BadSqlGrammarException e) {
            logger.trace("No Data found with profileId is {}  Sql Grammar Exception", profileId);
            throw new AppCommonException(4001 + "##Sql Grammar Exception"+deviceType+"##"+API_VERSION);
        } catch (TransientDataAccessException f) {
            logger.trace("No Data found with profileId is {} network or driver issue or db is temporarily unavailable  ", profileId);
            throw new AppCommonException(4002 + "##Network or driver issue or db is temporarily unavailable"+deviceType+"##"+API_VERSION);
        } catch (CannotGetJdbcConnectionException g) {
            logger.trace("No Data found with profileId is {} could not acquire a jdbc connection  ", profileId);
            throw new AppCommonException(4003 + "##A database connection could not be obtained"+deviceType+"##"+API_VERSION);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }



}
