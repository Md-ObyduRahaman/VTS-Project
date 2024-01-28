package nex.vts.backend.models.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class VehicleCurrentLocation {

    @JsonProperty("id")
    private int id;

    @JsonProperty("lat")
    private Double lat;

    @JsonProperty("lon")
    private Double longs;

    @JsonProperty("speed")
    private Double speed;

    @JsonProperty("head")
    private String head;

    @JsonProperty("timeStamp")
    private String timeStamp;

    @JsonProperty("datetime")
    private String datetime;

    @JsonProperty("engine")
    private String engine;

}