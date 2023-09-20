package nex.vts.backend.dbentities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NEX_CORPORATE_CLIENT {
    private String USER_ID, CLIENT_TYPE, COMPANY_NAME, COMPANY_ADDRESS, ADDRESS2, COMPANY_CELL, COMPANY_TEL, COMPANY_EMAIL;
}
