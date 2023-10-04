package nex.vts.backend.models.vehicle;

public class Vehicle_Road {


    private Integer roadId, districtId, thanaId;
    private String description, polyX, polyY, banglaDescription;


    public Vehicle_Road(Integer roadId, Integer districtId, Integer thanaId, String description, String polyX, String polyY, String banglaDescription) {
        this.roadId = roadId;
        this.districtId = districtId;
        this.thanaId = thanaId;
        this.description = description;
        this.polyX = polyX;
        this.polyY = polyY;
        this.banglaDescription = banglaDescription;
    }

    public Vehicle_Road() {
    }

    public Integer getRoadId() {
        return roadId;
    }

    public void setRoadId(Integer roadId) {
        this.roadId = roadId;
    }

    public Integer getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Integer districtId) {
        this.districtId = districtId;
    }

    public Integer getThanaId() {
        return thanaId;
    }

    public void setThanaId(Integer thanaId) {
        this.thanaId = thanaId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPolyX() {
        return polyX;
    }

    public void setPolyX(String polyX) {
        this.polyX = polyX;
    }

    public String getPolyY() {
        return polyY;
    }

    public void setPolyY(String polyY) {
        this.polyY = polyY;
    }

    public String getBanglaDescription() {
        return banglaDescription;
    }

    public void setBanglaDescription(String banglaDescription) {
        this.banglaDescription = banglaDescription;
    }
}
