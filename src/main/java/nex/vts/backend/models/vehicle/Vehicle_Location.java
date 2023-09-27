package nex.vts.backend.models.vehicle;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
@Getter
@Setter
@ToString
public class Vehicle_Location {
    private String vehicleId, vehicleName;
    private Float latitude, longitude;
    private String vehicleTime, engine;
    private Float speed;

    public Vehicle_Location(String vehicleId, String vehicleName, Float latitude, Float longitude, String vehicleTime, String engine, Float speed) {
        this.vehicleId = vehicleId;
        this.vehicleName = vehicleName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.vehicleTime = vehicleTime;
        this.engine = engine;
        this.speed = speed;
    }

    public Vehicle_Location() {
    }
}
