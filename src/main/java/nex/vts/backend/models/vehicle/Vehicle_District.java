package nex.vts.backend.models.vehicle;

public class Vehicle_District {
    private Integer districtId;
    private String description, polyX, polyY, banglaDescription;

    public Vehicle_District(Integer districtId, String description, String polyX, String polyY, String banglaDescription) {
        this.districtId = districtId;
        this.description = description;
        this.polyX = polyX;
        this.polyY = polyY;
        this.banglaDescription = banglaDescription;
    }

    public Vehicle_District() {
    }

    public Integer getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Integer districtId) {
        this.districtId = districtId;
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
