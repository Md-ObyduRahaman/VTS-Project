package nex.vts.backend.services;

import nex.vts.backend.models.responses.VehicleLocationResponse;
import nex.vts.backend.repoImpl.VehicleLocationRepo_Imp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Optional;

@Service
public class VehicleLocation_Service {
    private VehicleLocationRepo_Imp locationImplementation;
    @Autowired
    public VehicleLocation_Service(VehicleLocationRepo_Imp locationImplementation) {
        this.locationImplementation = locationImplementation;
    }


    /*    public VehicleLocation_Service(VehicleLocationRepo_Imp locationImplementation) {
        this.locationImplementation = locationImplementation;
    }*/

/*    public Optional getVehicleLatitudeLongitude(Double xLatitude, Double xLongitude) {
        return locationImplementation.getReverseGeocoder(xLatitude,xLongitude);
    }*/

/*    public VehicleLocationResponse getVehicleLocationDetails(Integer vehicleId)throws SQLException, BadSqlGrammarException, DataAccessException {
        locationResponse.vehicleLocation =  locationImplementation.getVehicleLocation(vehicleId);
        return locationResponse;
    }*/

/*    public Optional getVehicleDistrict()throws SQLException, BadSqlGrammarException, DataAccessException{
        Optional vehicleDistrict = locationImplementation.getVehicleDistrict();
        return vehicleDistrict;
    }*/

/*    public Object getVehicleThana(Integer thanaId)throws SQLException, BadSqlGrammarException, DataAccessException{
        Object vehicleThana = locationImplementation.getVehicleThana(thanaId);
        return vehicleThana;
    }*/

/*    public Optional getVehicleRoad(Integer districtId)throws SQLException, BadSqlGrammarException, DataAccessException{
        Optional vehicleRoad = locationImplementation.getVehicleRoad(districtId);
        return vehicleRoad;
    }*/


}