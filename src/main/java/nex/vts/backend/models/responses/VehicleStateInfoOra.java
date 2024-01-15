package nex.vts.backend.models.responses;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@JsonPropertyOrder({"vehId","vehStat","vehIconType", "dateTime", "locationDetails"})
public class VehicleStateInfoOra {

    private Integer vehId;
    private String enginStat;
    private String locationDetails;
    private String dateTime;
    private String vehStat;
}
