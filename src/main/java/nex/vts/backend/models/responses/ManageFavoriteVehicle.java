package nex.vts.backend.models.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ManageFavoriteVehicle  {
    @JsonProperty("P_TYPE")
    private String P_TYPE;
    @JsonProperty("P_VEHICLE_ID")
    private Integer P_VEHICLE_ID;
    @JsonProperty("P_FAVORITE_VALUE")
    private Integer P_FAVORITE_VALUE;


    @JsonProperty("profileType")
    private Integer profileType;
    @JsonProperty("profileId")
    private Integer profileId;
    @JsonProperty("parentId")
    private Integer parentId;




}
