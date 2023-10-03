package nex.vts.backend.services;

import nex.vts.backend.repoImpl.Vehicle_Location_Repo_Imp;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.Optional;

@Component
public class Vehicle_Location_Service {
    private Vehicle_Location_Repo_Imp locationImplementation;

    public Vehicle_Location_Service(Vehicle_Location_Repo_Imp locationImplementation) {
        this.locationImplementation = locationImplementation;
    }

    public Optional getVehicleLatitudeLongitude() {
        return locationImplementation.getReverseGeocoder();
    }

    public Optional getVehicleLocationDetails(Integer vehicleId)throws SQLException, BadSqlGrammarException, DataAccessException {
        return locationImplementation.getVehicleLocation(vehicleId);
    }

    public Optional getVehicleDistrict()throws SQLException, BadSqlGrammarException, DataAccessException{
        Optional vehicleDistrict = locationImplementation.getVehicleDistrict();
        return vehicleDistrict;
    }

    public Object getVehicleThana(Integer thanaId)throws SQLException, BadSqlGrammarException, DataAccessException{
        Object vehicleThana = locationImplementation.getVehicleThana(thanaId);
        return vehicleThana;
    }

    public Optional getVehicleRoad(Integer districtId)throws SQLException, BadSqlGrammarException, DataAccessException{
        Optional vehicleRoad = locationImplementation.getVehicleRoad(districtId);
        return vehicleRoad;
    }


}
