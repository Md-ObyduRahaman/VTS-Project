package nex.vts.backend.repositories;

import java.sql.SQLException;

public interface ModifyVehicleSettings_Repo {
    String modifyVehicleSettings(Integer profileType, Integer profileId,
                               Integer parentProfileId, Integer vehicleId, String maxSpeed,
                               String cellPhone, String email, int isFavourite,String schemaName) throws SQLException;
}
