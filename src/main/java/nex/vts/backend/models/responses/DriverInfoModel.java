package nex.vts.backend.models.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
public class DriverInfoModel {

    private String ID, USERID, D_NAME, D_FNAME,D_LICENSE,D_ADDRESS,D_CELL,D_DOB;
    private String DRIVER_PHOTO;
}
