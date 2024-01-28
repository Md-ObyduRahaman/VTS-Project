package nex.vts.backend.services;

import nex.vts.backend.models.responses.BaseResponse;
import nex.vts.backend.models.responses.ModifyVehicleSettingResponseMsg;
import nex.vts.backend.models.responses.ResponseMassage;
import nex.vts.backend.models.responses.VehicleConfigModel;
import nex.vts.backend.repositories.ModifyVehicleSettings_Repo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Optional;

@Service
public class ModifyVehicleSetting_Service {
    private Logger logger = LoggerFactory.getLogger(ModifyVehicleSetting_Service.class);
    private final ModifyVehicleSettings_Repo vehicleSettingsRepo;
    private ModifyVehicleSettingResponseMsg vehicleSettingResponseMsg = new ModifyVehicleSettingResponseMsg();
    private ResponseMassage responseMassage = new ResponseMassage();

    @Autowired
    public ModifyVehicleSetting_Service(ModifyVehicleSettings_Repo vehicleSettingsRepo) {
        this.vehicleSettingsRepo = vehicleSettingsRepo;
    }

    public ResponseMassage modifyVehicleSettingResponse( Integer profileType, Integer profileId,
                                                        Integer parentProfileId, Integer vehicleId, String maxSpeed,
                                                        String cellPhone, String email, int isFavourite,String schemaName) throws SQLException {


        if (!vehicleSettingsRepo.modifyVehicleSettings( profileType, profileId,
                parentProfileId, vehicleId, maxSpeed, cellPhone, email, isFavourite, schemaName).isEmpty()){

            String responseResult = vehicleSettingsRepo.modifyVehicleSettings( profileType, profileId,
                    parentProfileId, vehicleId, maxSpeed, cellPhone, email, isFavourite, schemaName);


            responseMassage.setMessage(responseResult);
            responseMassage.setVehicleSettingModify(true);
            vehicleSettingResponseMsg.setResponseMassage(responseMassage);

        }
        else {

            responseMassage.setMessage("Update fail");
            responseMassage.setVehicleSettingModify(false);
            vehicleSettingResponseMsg.setResponseMassage(responseMassage);

        }

        return vehicleSettingResponseMsg.getResponseMassage();
    }

    public VehicleConfigModel getVehicleSetting(Integer vehicleId){

        if (!vehicleSettingsRepo.getVehicleSettings(vehicleId).equals(null)){

            return vehicleSettingsRepo.getVehicleSettings(vehicleId);
        }
        else {
            return null;
        }
    }
}
