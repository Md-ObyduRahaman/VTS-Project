package nex.vts.backend.models.vehicle;

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


    @Override
    public String toString() {
        return "Vehicle_Location{" +
                "vehicleId='" + vehicleId + '\'' +
                ", vehicleName='" + vehicleName + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", vehicleTime='" + vehicleTime + '\'' +
                ", engine='" + engine + '\'' +
                ", speed=" + speed +
                '}';
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public String getVehicleTime() {
        return vehicleTime;
    }

    public void setVehicleTime(String vehicleTime) {
        this.vehicleTime = vehicleTime;
    }

    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    public Float getSpeed() {
        return speed;
    }

    public void setSpeed(Float speed) {
        this.speed = speed;
    }

}
