package nex.vts.backend.repoImpl;

import nex.vts.backend.dbentities.VTS_EXTENDED_USER_PROFILE;
import nex.vts.backend.models.responses.Case1UserInfo;
import nex.vts.backend.repositories.LoginUserInformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public class LoginUserInformation_Imp implements LoginUserInformation {

    private final Logger logger = LoggerFactory.getLogger(LoginUserInformation.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Optional<Case1UserInfo> caseOneAccountInfo(Integer profileId, String userName, String password,String dynamicColumnName,Integer operatorid) {
        String sql= "SELECT ID," +
                "       USER_ID," +
                "       COMPANY_NAME," +
                "       CLIENT_TYPE," +
                "       COMPANY_ADDRESS," +
                "       ADDRESS2," +
                "       COMPANY_CELL," +
                "       COMPANY_TEL," +
                "       COMPANY_EMAIL," +
                "       ACTIVATION" +
                "  FROM nex_corporate_client" +
                " WHERE     ID = ?" +
                "       AND USER_ID = ?" +
                "       AND "+dynamicColumnName+" = ?" +
                "       AND ACTIVATION = 1" +
                "       AND OPERATORID = "+operatorid;

        String sql2= "SELECT ID," +
                "       USER_ID," +
                "       COMPANY_NAME," +
                "       CLIENT_TYPE," +
                "       COMPANY_ADDRESS," +
                "       ADDRESS2," +
                "       COMPANY_CELL," +
                "       COMPANY_TEL," +
                "       COMPANY_EMAIL," +
                "       ACTIVATION" +
                "  FROM nex_corporate_client" +
                " WHERE  USER_ID = "+userName+"" +
                "       AND "+dynamicColumnName+" = "+password+"" +
                "       AND ACTIVATION = 1" +
                "       AND OPERATORID = 1";  //ID = "+profileId+""
        System.out.println(sql);


            logger.debug("Executing query to get parent profile id of extended type client {}", profileId);

            return  jdbcTemplate.queryForObject(sql, new Object[]{profileId,userName,password}, (rs, rowNum) ->
                    Optional.of(new Case1UserInfo(
                            rs.getString("ID"),
                            rs.getString("USER_ID"),
                            rs.getString("COMPANY_NAME"),
                            rs.getString("CLIENT_TYPE"),
                            rs.getString("COMPANY_ADDRESS"),
                            rs.getString("ADDRESS2"),
                            rs.getString("COMPANY_CELL"),
                            rs.getString("COMPANY_TEL"),
                            rs.getString("COMPANY_EMAIL"),
                            rs.getString("ACTIVATION")
                    ))
            );


    }
}
