package nex.vts.backend.models.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonPropertyOrder(value = {})
public class DetailsOfVehicleItem {
    @JsonProperty("rowNo")
    private int rowNo;
    @JsonProperty("distance")
    private String distance;
    @JsonProperty("groupId")
    private String groupId;
    @JsonProperty("positionHis")
    private String positionHis;
    @JsonProperty("lon")
    private float lon;
    @JsonProperty("favourite")
    private int favourite;
    @JsonProperty("userId")
    private String userId;
    @JsonProperty("speed")
    private int speed;
    @JsonProperty("engine")
    private String engine;
    @JsonProperty("iconType")
    private int iconType;
    @JsonProperty("orderIndex")
    private int orderIndex;
    @JsonProperty("id")
    private int id;
    @JsonProperty("vehicleId")
    private String vehicleId;
    @JsonProperty("vDate")
    private String vDate;
    @JsonProperty("lat")
    private float lat;

    public DetailsOfVehicleItem(int rowNo, int id, String vehicleId, String userId, String groupId, String engine, int speed, float lat, float lon, String vDate, int favourite, int iconType, int orderIndex, String distance, String positionHis) {
        this.rowNo = rowNo;
        this.id = id;
        this.vehicleId = vehicleId;
        this.userId = userId;
        this.groupId = groupId;
        this.engine = engine;
        this.speed = speed;
        this.lat = lat;
        this.lon = lon;
        this.vDate = vDate;
        this.favourite = favourite;
        this.iconType = iconType;
        this.orderIndex = orderIndex;
        this.distance = distance;
        this.positionHis = positionHis;
    }
}