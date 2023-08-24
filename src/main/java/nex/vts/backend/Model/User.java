package nex.vts.backend.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Long USER_ID;
    private Integer IS_ACTIVE;
    private String USERNAME, PASSWORD, EMAIL, ROLES;

}
