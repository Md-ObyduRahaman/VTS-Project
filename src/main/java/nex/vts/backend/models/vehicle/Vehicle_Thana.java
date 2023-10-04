package nex.vts.backend.models.vehicle;

public class Vehicle_Thana {
    private Integer thanaId, districtId;
    private String description, polyX, polyY, banglaDescription;
    public Vehicle_Thana(Integer thanaId, Integer districtId, String description, String polyX, String polyY, String banglaDescription) {
        this.thanaId = thanaId;
        this.districtId = districtId;
        this.description = description;
        this.polyX = polyX;
        this.polyY = polyY;
        this.banglaDescription = banglaDescription;
    }

    public Vehicle_Thana() {
    }

    public Integer getThanaId() {
        return thanaId;
    }

    public void setThanaId(Integer thanaId) {
        this.thanaId = thanaId;
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
