package nex.vts.backend.models.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"vehicleId", "vehicleName", "latitude", "longitude", "locationAddress", "vehicleTime", "speed", "engine"})
public class VehicleLocation {
    @JsonProperty("vehicleId")
    private String vehicleId;
    @JsonProperty("engine")
    private String engine;
    @JsonProperty("vehicleTime")
    private String vehicleTime;
/*    @JsonProperty("locationAddress")
    private String locationAddress;*/
    @JsonProperty("vehicleName")
    private String vehicleName;
    @JsonProperty("longitude")
    private Float longitude;
    @JsonProperty("speed")
    private Float speed;
    @JsonProperty("latitude")
    private Float latitude;

	public VehicleLocation(String vehicleId, String engine, String vehicleTime, /*String locationAddress,*/ String vehicleName, Float longitude, Float speed, Float latitude) {
		this.vehicleId = vehicleId;
		this.engine = engine;
		this.vehicleTime = vehicleTime;
//		this.locationAddress = locationAddress;
		this.vehicleName = vehicleName;
		this.longitude = longitude;
		this.speed = speed;
		this.latitude = latitude;
	}

	public String getVehicleId() {
        return vehicleId;
    }

    public String getEngine() {
        return engine;
    }

    public String getVehicleTime() {
        return vehicleTime;
    }

/*    public String getLocationAddress() {
        return locationAddress;
    }*/

    public String getVehicleName() {
        return vehicleName;
    }

    public float getLongitude() {
        return longitude;
    }

    public float getSpeed() {
        return speed;
    }

    public float getLatitude() {
        return latitude;
    }
}