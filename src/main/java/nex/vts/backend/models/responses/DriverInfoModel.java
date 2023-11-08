package nex.vts.backend.models.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DriverInfoModel {

    private String ID, USERID, D_NAME, D_FNAME,D_LICENSE,D_ADDRESS,D_CELL,D_DOB;
    private String DRIVER_HAS_PHOTO;
}
