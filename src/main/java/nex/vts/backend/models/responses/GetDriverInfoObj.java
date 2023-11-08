package nex.vts.backend.models.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Optional;

public class GetDriverInfoObj {

    @JsonProperty("GetDriverInfo")
    private Optional<GetDriverInfoObj> getDriverModels;

    public void setGetDriverModels(Optional<DriverInfoModel> getDriverInfo) {
    }

    // public static void setGetDriverModels(Optional<DriverInfoModel> getDriverInfo) {
   // }
}
