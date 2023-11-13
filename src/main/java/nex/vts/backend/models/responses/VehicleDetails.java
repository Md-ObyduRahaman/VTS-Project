package nex.vts.backend.models.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VehicleDetails {
    @JsonProperty("driverId")
    private String driverId;
    @JsonProperty("vehicleIconType")
    private int vehicleIconType;
    @JsonProperty("vendor")
    private String vendor;
    @JsonProperty("iconTypeStopped")
    private int iconTypeStopped;
    @JsonProperty("vehicleName")
    private String vehicleName;
    @JsonProperty("model")
    private String model;
    @JsonProperty("carImage")
    private String carImage;
    @JsonProperty("vehicleOptions")
    private String vehicleOptions;
    @JsonProperty("speed")
    private float speed;
    @JsonProperty("iconTypeRunning")
    private int iconTypeRunning;
    @JsonProperty("registrationNumber")
    private String registrationNumber;
    @JsonProperty("driverCell")
    private String driverCell;
    @JsonProperty("customUserid")
    private String customUserid;
    @JsonProperty("enginStatus")
    private String enginStatus;
    @JsonProperty("iconTypeStationary")
    private int iconTypeStationary;
    @JsonProperty("color")
    private String color;
    @JsonProperty("driverName")
    private String driverName;
    @JsonProperty("iconTypeOnMap")
    private int iconTypeOnMap;
    @JsonProperty("maxCarSpeed")
    private String maxCarSpeed;

    public VehicleDetails(String driverId, int vehicleIconType, String vendor, int iconTypeStopped, String vehicleName, String model, String carImage, String vehicleOptions, float speed, int iconTypeRunning, String registrationNumber, String driverCell, String customUserid, String enginStatus, int iconTypeStationary, String color, String driverName, int iconTypeOnMap, String maxCarSpeed) {
        this.driverId = driverId;
        this.vehicleIconType = vehicleIconType;
        this.vendor = vendor;
        this.iconTypeStopped = iconTypeStopped;
        this.vehicleName = vehicleName;
        this.model = model;
        this.carImage = carImage;
        this.vehicleOptions = vehicleOptions;
        this.speed = speed;
        this.iconTypeRunning = iconTypeRunning;
        this.registrationNumber = registrationNumber;
        this.driverCell = driverCell;
        this.customUserid = customUserid;
        this.enginStatus = enginStatus;
        this.iconTypeStationary = iconTypeStationary;
        this.color = color;
        this.driverName = driverName;
        this.iconTypeOnMap = iconTypeOnMap;
        this.maxCarSpeed = maxCarSpeed;
    }

    public String getDriverId() {
        return driverId;
    }

    public int getVehicleIconType() {
        return vehicleIconType;
    }

    public String getVendor() {
        return vendor;
    }

    public int getIconTypeStopped() {
        return iconTypeStopped;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public String getModel() {
        return model;
    }

    public String getCarImage() {
        return carImage;
    }

    public String getVehicleOptions() {
        return vehicleOptions;
    }

    public float getSpeed() {
        return speed;
    }

    public int getIconTypeRunning() {
        return iconTypeRunning;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public String getDriverCell() {
        return driverCell;
    }

    public String getCustomUserid() {
        return customUserid;
    }

    public String getEnginStatus() {
        return enginStatus;
    }

    public int getIconTypeStationary() {
        return iconTypeStationary;
    }

    public String getColor() {
        return color;
    }

    public String getDriverName() {
        return driverName;
    }

    public int getIconTypeOnMap() {
        return iconTypeOnMap;
    }

    public String getMaxCarSpeed() {
        return maxCarSpeed;
    }
}