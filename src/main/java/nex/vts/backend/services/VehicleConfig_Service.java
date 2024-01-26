//package nex.vts.backend.services;
//
//import nex.vts.backend.models.responses.VehicleConfigResponse;
//import nex.vts.backend.repositories.VehicleConfig_Repo;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service
//public class VehicleConfig_Service {
//
//    Logger logger = LoggerFactory.getLogger(VehicleConfig_Service.class);
//
//    VehicleConfigResponse configResponse = new VehicleConfigResponse();
//    private final VehicleConfig_Repo configRepo;
//
//    @Autowired
//    public VehicleConfig_Service(VehicleConfig_Repo configRepo) {
//        this.configRepo = configRepo;
//    }
//
//
//    public VehicleConfigResponse setVehicleSettings(String cellPhone,
//                                                    String email,
//                                                    String maxCarSpeed,
//                                                    int isFavourite,
//                                                    Integer vehicleId){
//
//        configRepo.getVehicleSettings(vehicleId);
//
//        configRepo.setVehicleSettings(cellPhone, email, maxCarSpeed, isFavourite, vehicleId);
//
//        configResponse.setVehicleSettingResponse("Settings Updated");
//
//        return configResponse;
//
//        }
//
//
//    }
//
