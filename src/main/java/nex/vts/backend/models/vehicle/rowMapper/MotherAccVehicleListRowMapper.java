package nex.vts.backend.models.vehicle.rowMapper;

import nex.vts.backend.models.responses.MotherAccVehicleList;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MotherAccVehicleListRowMapper implements RowMapper<MotherAccVehicleList> {
    @Override
    public MotherAccVehicleList mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new MotherAccVehicleList(
                rs.getInt("ROWNO"),
                rs.getInt("ID"),
                rs.getString("VEHICLE_ID"),
                rs.getString("USERID"),
                rs.getString("GROUP_ID"),
                rs.getString("ENGIN"),
                rs.getInt("SPEED"),
                rs.getFloat("LAT"),
                rs.getFloat("LON"),
                rs.getString("VDATE"),
                rs.getInt("FAVORITE"),
                rs.getInt("ICON_TYPE"),
                rs.getInt("ORDER_INDEX"),
                rs.getString("DISTANCE"),
                rs.getString("POSITION_HIS"),
                rs.getInt("iscolor")
        );
    }
}