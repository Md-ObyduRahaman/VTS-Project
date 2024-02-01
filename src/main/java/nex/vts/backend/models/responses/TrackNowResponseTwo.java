package nex.vts.backend.models.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TrackNowResponseTwo {

    @JsonProperty("id")
    private int id;

    @JsonProperty("lat")
    private Double lat;

    @JsonProperty("lon")
    private Double longs;

    @JsonProperty("speed")
    private Float speed;

    @JsonProperty("head")
    private String head;

    @JsonProperty("timeStamp")
    private String timeStamp;

    @JsonProperty("datetime")
    private String datetime;

    @JsonProperty("engine")
    private String engine;

    @JsonProperty("locationPoi")
    private String locDetails;
}
