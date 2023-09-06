package nex.vts.backend.dbentities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VTS_LOGIN_USER {
    private long ID,PROFILE_ID,MAIN_ACCOUNT_ID;
    private int USER_TYPE,ROLE_ID,IS_ACCOUNT_ACTIVE,IS_REMOTE_ACCESS_ENABLED;
    private String USERNAME,PASSWORD;
}
