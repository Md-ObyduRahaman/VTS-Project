package nex.vts.backend.models.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class VehicleInfos {

/*    @JsonProperty("rowNo")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private int rowNo;

    @JsonProperty("distance")
    private String distance;

    @JsonProperty("groupId")
    private String groupId;

    @JsonProperty("positionHis")
    private String positionHis;





    @JsonProperty("userId")
    private String userId;







;





    @JsonProperty("vDate")
    private String vDate;



    @JsonProperty("iscolor")
    private int iscolor;*/

/*    public MotherAccVehicleList(int rowNo, int id, String vehicleId, String userId, String groupId, String engine, int speed, float lat, float lon, String vDate, int favourite, int iconType, int orderIndex, String distance, String positionHis, int iscolor) {
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
        this.iscolor = iscolor;
    }*/

    @JsonProperty("id")
    private int id;

    @JsonProperty("vehicleId")
    private String vehicleId;

    @JsonProperty("engine")
    private String engine;

    @JsonProperty("speed")
    private float speed;

    @JsonProperty("lat")
    private float lat;

    @JsonProperty("lon")
    private float lon;

    @JsonProperty("favourite")
    private int favourite;

    @JsonProperty("iconType")
    private int iconType;

    @JsonProperty("orderIndex")
    private int orderIndex;

    @JsonProperty("userId")
    private String userId;

}