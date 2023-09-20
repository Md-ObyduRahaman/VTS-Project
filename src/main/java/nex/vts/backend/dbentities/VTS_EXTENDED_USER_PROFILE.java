package nex.vts.backend.dbentities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*Stores profile information of a user.*/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VTS_EXTENDED_USER_PROFILE {
    private int PARENT_PROFILE_ID;
}
