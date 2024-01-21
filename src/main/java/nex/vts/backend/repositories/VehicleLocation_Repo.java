package nex.vts.backend.repositories;


public interface VehicleLocation_Repo {
//    VehicleLocation getVehicleLocation(Integer vehicleId)throws SQLException, BadSqlGrammarException, DataAccessException;
    Object getReverseGeocoder(Double xLatitude,Double xLongitude);
//    Optional getVehicleDistrict() throws SQLException, BadSqlGrammarException, DataAccessException;
//    Object getVehicleThana(Integer thanaId) throws SQLException, BadSqlGrammarException, DataAccessException;
//    Optional getVehicleRoad(Integer districtId)throws SQLException, BadSqlGrammarException, DataAccessException;
}