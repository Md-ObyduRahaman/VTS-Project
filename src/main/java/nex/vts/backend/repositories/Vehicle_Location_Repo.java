package nex.vts.backend.repositories;


import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.BadSqlGrammarException;

import java.sql.SQLException;
import java.util.Optional;

public interface Vehicle_Location_Repo {
    Optional<Object> getVehicleLocation(Integer vehicleId)throws SQLException, BadSqlGrammarException, DataAccessException;
    Optional getReverseGeocoder(Double xLatitude,Double xLongitude);
    Optional getVehicleDistrict() throws SQLException, BadSqlGrammarException, DataAccessException;
    Object getVehicleThana(Integer thanaId) throws SQLException, BadSqlGrammarException, DataAccessException;
    Optional getVehicleRoad(Integer districtId)throws SQLException, BadSqlGrammarException, DataAccessException;
}