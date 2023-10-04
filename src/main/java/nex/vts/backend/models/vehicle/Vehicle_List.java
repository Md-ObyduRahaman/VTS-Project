package nex.vts.backend.models.vehicle;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Vehicle_List {

    private String vehicleId;
    private String vehicleName;
    private String engine;
    private Float speed;
    private Integer favourite;
    private Integer iconType;
    private String customUserId;
    private Integer iconTypeOnMap;
    private Integer iconTypeRunning;
    private Integer iconTypeStopped;
    private Integer iconTypeStationary;
    private Integer maxSpeed;

}
