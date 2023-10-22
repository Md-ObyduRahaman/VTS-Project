package nex.vts.backend.models.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MonthTravleDistance {
    private String profileId, vehicleId, dateTime, numOfDays, distance, mainAccountId;
    private Integer key;
}
