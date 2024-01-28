package nex.vts.backend.models.responses;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@JsonPropertyOrder({"vehId","vehName","vehIconType", "dateTime","vehStat", "locationPoi"})
public class VehicleStateInfoOra {

    private Integer vehId;
    private String vehName;
    private String enginStat;
    private String locationPoi;
    private String dateTime;
    private String vehStat;
    private String vehIconType;
}
