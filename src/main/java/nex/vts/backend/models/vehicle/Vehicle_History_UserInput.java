package nex.vts.backend.models.vehicle;

public class Vehicle_History_UserInput {
    private Integer vehicleId;
    private String fromDate, toDate;

    public Vehicle_History_UserInput(Integer vehicleId, String fromDate, String toDate) {
        this.vehicleId = vehicleId;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public Vehicle_History_UserInput() {
    }

    public Integer getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Integer vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    @Override
    public String toString() {
        return "Vehicle_History{vehicleId=" + vehicleId + ", fromDate='" + fromDate + '\'' + ", toDate='" + toDate + '\'' + '}';
    }
}
