package nex.vts.backend.repoImpl;

import nex.vts.backend.models.responses.VehicleLocation;
import nex.vts.backend.models.vehicle.Vehicle_District;
import nex.vts.backend.models.vehicle.Vehicle_Road;
import nex.vts.backend.models.vehicle.Vehicle_Thana;
import nex.vts.backend.models.vehicle.rowMapper.Vehicle_District_RowMapper;
import nex.vts.backend.models.vehicle.rowMapper.Vehicle_Location_RowMapper;
import nex.vts.backend.models.vehicle.rowMapper.Vehicle_Road_RowMapper;
import nex.vts.backend.models.vehicle.rowMapper.Vehicle_Thana_RowMapper;
import nex.vts.backend.repositories.Vehicle_Location_Repo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class Vehicle_Location_Repo_Imp implements Vehicle_Location_Repo {
    private final JdbcTemplate jdbcTemplate;
    private final static Logger LOGGER = LoggerFactory.getLogger(Vehicle_Location_Repo_Imp.class);

    public Vehicle_Location_Repo_Imp(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public VehicleLocation getVehicleLocation(Integer vehicleId) throws SQLException, BadSqlGrammarException, DataAccessException {
        String getQuery = "select a.vehicle_id    vehicle_id,\n       a.userid             vehicle_name,\n       a.lat                                                                                latitude,\n       a.lon                                                                                longitude,\n       to_char(to_date(a.vdate, 'YYYY-MM-DD HH24:MI:SS') - 2 / 24, 'YYYY-MM-DD HH24:MI:SS') vehicle_time,\n       a.engin                                                                              engine,\n       a.speed                                                                              speed\nFROM GPSNEXGP.NEX_INDIVIDUAL_TEMP a,\n     GPSNEXGP.NEX_INDIVIDUAL_CLIENT b\nwhere a.vehicle_id = b.id\n  AND a.vehicle_id = ?";
        return  jdbcTemplate.queryForObject(getQuery, new Vehicle_Location_RowMapper(),new Object[]{vehicleId});
//        return Optional.of(getVehicleLocation);
    }/*todo ------ get vehicle location ------------*/

    @Override
    public Optional getReverseGeocoder(Double xLatitude, Double xLongitude) {
        String districtName;
        String[] listOfPolyX, listOfPolyY;
        Integer numberOfPolyX, districtId;
        List<Vehicle_District> listOfDistrict = new ArrayList<>();
        String getDistrict = "SELECT ID, DESCRIPTION, POLYX, POLYY, DESCRIPTION_B FROM GPSNEXGP.DISTRICT";
        listOfDistrict = jdbcTemplate.query(getDistrict, new Vehicle_District_RowMapper());
        if (!listOfDistrict.isEmpty())
            for (int eachDistrict = 0; eachDistrict < listOfDistrict.size(); eachDistrict++) {
                String discription = listOfDistrict.get(eachDistrict).getDescription();
                listOfPolyX = getCSVValues(listOfDistrict.get(eachDistrict).getPolyX());
                listOfPolyY = getCSVValues(listOfDistrict.get(eachDistrict).getPolyY());
                numberOfPolyX = listOfPolyX.length;
                if (pointInPolygon(xLatitude, xLongitude, numberOfPolyX, listOfPolyX, listOfPolyY) == false) {
                    /*Todo---- implement reversegeocoder*/
                }
            }
        return Optional.empty();
    }

    private boolean pointInPolygon(Double xLatitude, Double xLongitude, Integer numberOfPolyX, String[] listOfPolyX, String[] listOfPolyY) {
        int j = numberOfPolyX - 1;
        boolean oddNodes = false;
        for (int i = 0; i < numberOfPolyX; i++) {
            if ((Double.parseDouble(listOfPolyY[i]) < xLongitude && Double.parseDouble(listOfPolyY[j]) >= xLongitude || Double.parseDouble(listOfPolyY[j]) < xLongitude && Double.parseDouble(listOfPolyY[i]) >= xLongitude) && Double.parseDouble(listOfPolyX[i]) + (xLongitude - Double.parseDouble(listOfPolyY[i])) / (Double.parseDouble(listOfPolyY[j]) - Double.parseDouble(listOfPolyY[i])) * (Double.parseDouble(listOfPolyY[j]) - Double.parseDouble(listOfPolyX[i])) < xLatitude)
                oddNodes = !oddNodes;
            j = i;
        }
        return oddNodes;
    }

    @Override
    public Optional getVehicleDistrict() throws SQLException, BadSqlGrammarException, DataAccessException {
        String getQuery = "SELECT ID, DESCRIPTION, POLYX, POLYY, DESCRIPTION_B\n" + "FROM GPSNEXGP.DISTRICT";
        List<Vehicle_District> vehicleDistricts = jdbcTemplate.query(getQuery, new Vehicle_District_RowMapper());
        return Optional.of(vehicleDistricts);
    }

    @Override
    public Object getVehicleThana(Integer thanaId) throws SQLException, BadSqlGrammarException, DataAccessException {
        if (!thanaId.equals(null)) try {
            String getQuery = "SELECT /*ID,*/ DIST_ID, DESCRIPTION, POLYX, POLYY, DESCRIPTION_B\n" + "FROM GPSNEXGP.thana\n".concat("where DIST_ID = ?");
            List<Vehicle_Thana> vehicleThanas = jdbcTemplate.query(getQuery, new Vehicle_Thana_RowMapper(), thanaId);
            return Optional.of(vehicleThanas);
        } catch (Exception exception) {
            return LOGGER.getName();
        }
        else {
            String getQuery = "SELECT ID, DIST_ID, DESCRIPTION, POLYX, POLYY, DESCRIPTION_B\n" + "FROM GPSNEXGP.thana";
            List<Vehicle_Thana> vehicleThanas = jdbcTemplate.query(getQuery, new Vehicle_Thana_RowMapper());
            return Optional.of(vehicleThanas);
        }
    }

    @Override
    public Optional getVehicleRoad(Integer districtId) throws SQLException, BadSqlGrammarException, DataAccessException {
        if (districtId.equals(null)) {
            String getQuery = "SELECT ID, DIST_ID, THANA_ID, DESCRIPTION, POLYX, POLYY, DESCRIPTION_B\nFROM GPSNEXGP.road";
            List<Vehicle_Road> vehicleRoads = jdbcTemplate.query(getQuery, new Vehicle_Road_RowMapper());
            return Optional.of(vehicleRoads);
        } else {
            String getQuery = "SELECT ID, DIST_ID, THANA_ID, DESCRIPTION, POLYX, POLYY, DESCRIPTION_B\n" + "FROM GPSNEXGP.road".concat(" where DIST_ID = ?");
            List<Vehicle_Road> vehicleRoads = jdbcTemplate.query(getQuery, new Vehicle_Road_RowMapper(), districtId);
            return Optional.of(vehicleRoads);
        }
    }

    private String[] getCSVValues(String poly) { /*Todo : in here php code  separeted double quoted vlaue if exists*/
        String[] listOfPolyX = poly.split(",");
        return listOfPolyX;
    }
}