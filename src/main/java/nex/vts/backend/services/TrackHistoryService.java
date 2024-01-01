package nex.vts.backend.services;

import nex.vts.backend.exceptions.AppCommonException;
import nex.vts.backend.models.responses.TrackNowResponse;
import nex.vts.backend.repositories.TrackNowRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class TrackHistoryService {
    private final short API_VERSION = 1;

    TrackNowResponse trackNowResponse = new TrackNowResponse();
    TrackNowRepository trackNowRepository;

    @Autowired
    public TrackHistoryService(TrackNowRepository trackNowRepository) {
        this.trackNowRepository = trackNowRepository;
    }

    public Optional<TrackNowResponse> getVehiclePresentLocation(Integer vehicleId, Integer operatorId){

        switch (operatorId){

            case 1:/*TODO GP-1*/
            case 3:/*TODO M2M-3*/

                if (vehicleId != null) {

                    trackNowResponse.setVehicleCurrentLocation(trackNowRepository.getCurrentLocation(vehicleId));

                } else {

                    throw new AppCommonException(404 + "##Required field is Missing##" + null + "##" + API_VERSION);
                }
        }

        return Optional.ofNullable(trackNowResponse);
    }
}
