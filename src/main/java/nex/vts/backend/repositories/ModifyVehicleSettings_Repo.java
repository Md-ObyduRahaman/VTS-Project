package nex.vts.backend.repositories;

import nex.vts.backend.models.responses.VehicleConfigModel;

import java.sql.SQLException;
import java.util.Optional;

public interface ModifyVehicleSettings_Repo {
    String modifyVehicleSettings(Integer profileType, Integer profileId,
                               Integer parentProfileId, Integer vehicleId, String maxSpeed,
                               String cellPhone, String email, int isFavourite,String schemaName) throws SQLException;

    VehicleConfigModel getVehicleSettings(Integer vehicleId);
}
