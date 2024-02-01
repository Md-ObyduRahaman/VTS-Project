package nex.vts.backend.services;

import nex.vts.backend.exceptions.AppCommonException;
import nex.vts.backend.models.responses.TrackNowResponse;
import nex.vts.backend.models.responses.TrackNowResponseTwo;
import nex.vts.backend.models.responses.VehicleCurrentLocation;
import nex.vts.backend.repositories.TrackNowRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static nex.vts.backend.utilities.ExtractLocationLib.get_Location;

@Component
public class TrackHistoryService {
    private final short API_VERSION = 1;

    TrackNowResponse trackNowResponse = new TrackNowResponse();
    TrackNowRepository trackNowRepository;
    TrackNowResponseTwo responseTwo = new TrackNowResponseTwo();

    @Autowired
    public TrackHistoryService(TrackNowRepository trackNowRepository) {
        this.trackNowRepository = trackNowRepository;
    }

    public Optional<TrackNowResponse> getVehiclePresentLocation(Integer vehicleId, Integer operatorId){

        switch (operatorId){

            case 1:/*TODO GP-1*/
            case 3:/*TODO M2M-3*/

                if (vehicleId != null) {

                    Optional<VehicleCurrentLocation> currentLocation =  trackNowRepository.getCurrentLocation(vehicleId);

                    String dateTime = currentLocation.get().getDatetime();
//                    dateTime =  responseDateTime(dateTime);

                    responseTwo.setId(currentLocation.get().getId());
                    responseTwo.setHead(currentLocation.get().getHead());
                    responseTwo.setEngine(currentLocation.get().getEngine());
                    responseTwo.setLongs(currentLocation.get().getLongs());
                    responseTwo.setLat(currentLocation.get().getLat());
                    responseTwo.setHead(currentLocation.get().getHead());
                    currentLocation.get().setDatetime(responseDateTime(dateTime));
                    currentLocation.get().setTimeStamp(responseDateTime(dateTime));
                    responseTwo.setDatetime(currentLocation.get().getDatetime());
                    responseTwo.setTimeStamp(currentLocation.get().getTimeStamp());
                    responseTwo.setLocDetails(get_Location(
                            String.valueOf(currentLocation.get().getLat()),
                            String.valueOf(currentLocation.get().getLongs())
                    ));
                    responseTwo.setSpeed(currentLocation.get().getSpeed());

                    trackNowResponse.setVehicleCurrentLocation(Optional.ofNullable(responseTwo));



                } else {

                    throw new AppCommonException(404 + "##Required field is Missing##" + null + "##" + API_VERSION);
                }
        }

        return Optional.ofNullable(trackNowResponse);
    }

    public String responseDateTime(String dateTime){

        String newDates = dateTime.substring(0,10);
        LocalDate NewDates = LocalDate.parse(newDates);
        String newTimes = dateTime.substring(11);
        LocalTime NewTimes = LocalTime.parse(newTimes);

       String newDateTimes = newDates.concat(" ")
               .concat(String.valueOf(NewTimes.minusHours(2)));

//        LocalDate newDate = LocalDate.parse(String.valueOf(dateTime).substring(0, 8),
//                DateTimeFormatter.ofPattern("yyyyMMdd"));
//
//        LocalTime newTime = LocalTime.parse(String.valueOf(dateTime).substring(8),
//                DateTimeFormatter.ofPattern("HHmmss")).minusHours(2);
//
//        String time, dateTimes;
//
//        if (newTime.getMinute() != 0) {
//
//            time = String.valueOf(newTime);
//        } else {
//
//            time = String.valueOf(newTime).concat(":").concat("00");
//        }
//
//        dateTimes = String.valueOf(newDate).replace("-", "")
//                .concat(time.replace(":", ""));

        return newDateTimes /*Long.parseLong(dateTimes)*/;
    }
}
