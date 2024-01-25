package nex.vts.backend.repositories;


public interface VehicleDetails_Repo {

    Object get_VehicleDetail_For_GpAndM2M(Integer userType, Integer vehRowId, String schemaName);

//    Object getVehiclePermission(Integer userType, Integer profileId, Integer parentId, Integer vehicleId);

}
