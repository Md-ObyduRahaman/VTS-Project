package nex.vts.backend.services;

import nex.vts.backend.exceptions.AppCommonException;
import nex.vts.backend.models.responses.HistoriesItem;
import nex.vts.backend.models.responses.History;
import nex.vts.backend.models.responses.VehicleHistoryResponse;
import nex.vts.backend.repositories.VehicleHistoryRepo;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class VehicleHistory_Service {
    private final VehicleHistoryRepo history_repo;
    History history = new History();
    VehicleHistoryResponse vehicleHistoryResponse = new VehicleHistoryResponse();

    public VehicleHistory_Service(VehicleHistoryRepo historyRepo) {
        history_repo = historyRepo;
    }

    @SuppressWarnings("unchecked")
    public VehicleHistoryResponse getVehicleHistory(Integer vehicleId,String fromDateTime,String toDateTime,String schemaName,Integer operatorId){

        try {

            switch (operatorId){
            case 1: /*TODO GP*/
            case 3: /*TODO M2M*/

                List<HistoriesItem> historiesItemList = (List<HistoriesItem>) history_repo.getVehicleHistoryForGpAndM2M(vehicleId,
                        Long.parseLong(fromDateTime(fromDateTime)), Long.parseLong(toDateTime(toDateTime)), schemaName);

                history.setHistories(historiesItemResponse(historiesItemList));
                history.setTotalCount(historiesItemList.size());
                history.setCode(200);
                vehicleHistoryResponse.setHistory(history);
            }
        }catch (Exception e){

            throw new AppCommonException(e.getMessage());
        }

        return vehicleHistoryResponse;
    }

    public List<HistoriesItem> historiesItemResponse(List<HistoriesItem> historiesItemList){

        historiesItemList.forEach(
                historiesItem -> {
                    Long aLong = responseDateTime(historiesItem.getTimeInNumber());
                    historiesItem.setTimeInNumber(aLong);
                }
        );

        return historiesItemList;
    }

    public String fromDateTime(String fromDateTime){

        LocalDate newfromDate = LocalDate.parse(fromDateTime.substring(0, 8),
                DateTimeFormatter.ofPattern("yyyyMMdd"));

        LocalTime localTime_new_fromTime = LocalTime.parse(fromDateTime.substring(8),
                DateTimeFormatter.ofPattern("HHmmss")).plusHours(2);

        String fromDate,fromTime,fromDateTimes;

        if (localTime_new_fromTime.getMinute() != 0) {

            fromTime = String.valueOf(localTime_new_fromTime);
        } else {

            fromTime = String.valueOf(localTime_new_fromTime).concat(":").concat("00");
        }

        fromDateTimes = String.valueOf(newfromDate).replace("-", "")
                .concat(fromTime.replace(":", ""));

        return fromDateTimes;
    }

    public String toDateTime(String toDateTime){

        LocalDate newToDate = LocalDate.parse(toDateTime.substring(0, 8),
                DateTimeFormatter.ofPattern("yyyyMMdd"));

        LocalTime newToTime = LocalTime.parse(toDateTime.substring(8),
                DateTimeFormatter.ofPattern("HHmmss")).plusHours(2);

        String toTime, toDateTimes;

        if (newToTime.getMinute() != 0) {

            toTime = String.valueOf(newToTime);
        } else {

            toTime = String.valueOf(newToTime).concat(":").concat("00");
        }

        toDateTimes = String.valueOf(newToDate).replace("-", "")
                .concat(toTime.replace(":", ""));

        return toDateTimes;
    }

    public Long responseDateTime(Long dateTime){

        LocalDate newDate = LocalDate.parse(String.valueOf(dateTime).substring(0, 8),
                DateTimeFormatter.ofPattern("yyyyMMdd"));

        LocalTime newTime = LocalTime.parse(String.valueOf(dateTime).substring(8),
                DateTimeFormatter.ofPattern("HHmmss")).minusHours(2);

        String time, dateTimes;

        if (newTime.getMinute() != 0) {

            time = String.valueOf(newTime);
        } else {

            time = String.valueOf(newTime).concat(":").concat("00");
        }

        dateTimes = String.valueOf(newDate).replace("-", "")
                .concat(time.replace(":", ""));

        return Long.parseLong(dateTimes);
    }
}
