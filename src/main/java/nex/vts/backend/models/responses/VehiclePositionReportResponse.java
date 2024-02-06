package nex.vts.backend.models.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class VehiclePositionReportResponse {

    @JsonProperty("Number Of Records")
    private int numberOfRecords;

    @JsonProperty("Vehicle Position Report Data")
    private List<VehiclePositionReportData> reportData;
}
