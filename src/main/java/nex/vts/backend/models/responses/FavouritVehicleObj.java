package nex.vts.backend.models.responses;

import com.fasterxml.jackson.annotation.JsonProperty;


import java.util.ArrayList;
import java.util.Optional;

public class FavouritVehicleObj {

    @JsonProperty("FavouriteVehiclelList")
    private Optional<ArrayList<FavouriteVehiclelModel>> favouriteVehiclelModels;

    public void setFavouriteVehiclelModels(Optional<ArrayList<FavouriteVehiclelModel>> favouriteVehiclelModels) {
        this.favouriteVehiclelModels = favouriteVehiclelModels;
    }
}
