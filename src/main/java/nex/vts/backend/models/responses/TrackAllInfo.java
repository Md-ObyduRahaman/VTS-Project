package nex.vts.backend.models.responses;

import lombok.Data;

@Data
public class TrackAllInfo {
    private String vehId,vehName,lat, lng, speed, date, time, engin,iconType;
}
