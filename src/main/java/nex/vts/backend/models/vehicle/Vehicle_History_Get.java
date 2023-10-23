package nex.vts.backend.models.vehicle;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Vehicle_History_Get {
    private String maxSpeed;
    private Long rowNo, id;
    private String vehicleId, groupId, deviceId, timeStamp;
    private Double latitude, longitude;
    private Long dateTime;
    private String engineStatus, speed; /*    public Vehicle_History_Get(String maxSpeed, Long rowNo, Long id, String vehicleId, String groupId, String deviceId, String timeStamp, Double latitude, Double longitude, Long dateTime, String engineStatus, String speed) { this.maxSpeed = maxSpeed; this.rowNo = rowNo; this.id = id; this.vehicleId = vehicleId; this.groupId = groupId; this.deviceId = deviceId; this.timeStamp = timeStamp; this.latitude = latitude; this.longitude = longitude; this.dateTime = dateTime; this.engineStatus = engineStatus; this.speed = speed; } public Vehicle_History_Get() { } public String getMaxSpeed() { return maxSpeed; } public void setMaxSpeed(String maxSpeed) { this.maxSpeed = maxSpeed; } public Long getRowNo() { return rowNo; } public void setRowNo(Long rowNo) { this.rowNo = rowNo; } public Long getId() { return id; } public void setId(Long id) { this.id = id; } public String getVehicleId() { return vehicleId; } public void setVehicleId(String vehicleId) { this.vehicleId = vehicleId; } public String getGroupId() { return groupId; } public void setGroupId(String groupId) { this.groupId = groupId; } public String getDeviceId() { return deviceId; } public void setDeviceId(String deviceId) { this.deviceId = deviceId; } public String getTimeStamp() { return timeStamp; } public void setTimeStamp(String timeStamp) { this.timeStamp = timeStamp; } public Double getLatitude() { return latitude; } public void setLatitude(Double latitude) { this.latitude = latitude; } public Double getLongitude() { return longitude; } public void setLongitude(Double longitude) { this.longitude = longitude; } public Long getDateTime() { return dateTime; } public void setDateTime(Long vehicleDateTime) { this.dateTime = dateTime; } public String getEngineStatus() { return engineStatus; } public void setEngineStatus(String engineStatus) { this.engineStatus = engineStatus; } public String getSpeed() { return speed; } public void setSpeed(String speed) { this.speed = speed; } @Override public String toString() { return "Vehicle_History_Get{maxSpeed='" + maxSpeed + '\'' + ", rowNo=" + rowNo + ", id=" + id + ", vehicleId='" + vehicleId + '\'' + ", groupId='" + groupId + '\'' + ", deviceId='" + deviceId + '\'' + ", timeStamp='" + timeStamp + '\'' + ", latitude=" + latitude + ", longitude=" + longitude + ", dateTime=" + dateTime + ", engineStatus='" + engineStatus + '\'' + ", speed='" + speed + '\'' + '}'; }*/
}
