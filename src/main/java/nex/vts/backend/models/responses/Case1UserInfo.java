package nex.vts.backend.models.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Case1UserInfo {
    private String ID, USER_ID, COMPANY_NAME, CLIENT_TYPE, COMPANY_ADDRESS, ADDRESS2,
            COMPANY_CELL, COMPANY_TEL, COMPANY_EMAIL, ACTIVATION;
}
