package nex.vts.backend.repositories;

import nex.vts.backend.models.responses.FavouriteVehiclelModel;

import java.util.ArrayList;
import java.util.Optional;

public interface FavouriteVehiclelRepo {

   public Optional<ArrayList<FavouriteVehiclelModel>> findNeededData(String limit, Integer offset, Integer GROUP_ID, Integer OPERATORID, Integer userType, Integer PARENT_PROFILE_ID,Integer deviceType);

}
