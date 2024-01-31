package nex.vts.backend.models.responses;

import lombok.Data;

@Data
public class TrackAllInfo {

    private String lat, lng, speed, direction, time, date, engin;
}
