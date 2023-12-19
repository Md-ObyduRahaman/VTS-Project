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

        LocalDate localDate_new_fromDate = LocalDate.parse(fromDateTime.substring(0, 8), DateTimeFormatter.ofPattern("yyyyMMdd"));
        LocalDate localDate_new_toDate = LocalDate.parse(toDateTime.substring(0, 8), DateTimeFormatter.ofPattern("yyyyMMdd"));
        LocalTime localTime_new_fromTime = LocalTime.parse(fromDateTime.substring(8), DateTimeFormatter.ofPattern("HHmmss")).plusHours(2);
        LocalTime localTime_new_toTime = LocalTime.parse(toDateTime.substring(8), DateTimeFormatter.ofPattern("HHmmss")).plusHours(2);

        String fromTime;
        String toTime;
        String fromDateTimes;
        String toDateTimes;
        String from_Date;
        String to_Date;

        if (localTime_new_fromTime.getMinute() != 0) {

            fromTime = String.valueOf(localTime_new_fromTime);
        } else {

            fromTime = String.valueOf(localTime_new_fromTime).concat(":").concat("00");
        }

        if (localTime_new_toTime.getMinute() != 0) {

            toTime = String.valueOf(localTime_new_toTime);
        } else {

            toTime = String.valueOf(localTime_new_toTime).concat(":").concat("00");
        }

        from_Date = String.valueOf(localDate_new_fromDate);
        to_Date = String.valueOf(localDate_new_toDate);
        fromDateTimes = from_Date.replace("-", "").concat(fromTime.replace(":", ""));
        toDateTimes = to_Date.replace("-", "").concat(toTime.replace(":", ""));

        try {

            switch (operatorId){
            case 1: /*TODO GP*/
            case 3: /*TODO M2M*/

                List<HistoriesItem> historiesItemList = (List<HistoriesItem>) history_repo.getVehicleHistoryForGpAndM2M(vehicleId, Long.parseLong(fromDateTimes), Long.parseLong(toDateTimes), schemaName);
                history.setHistories(historiesItemList);
                history.setTotalCount(historiesItemList.size());
                history.setCode(200);
                vehicleHistoryResponse.setHistory(history);
            }
        }catch (Exception e){

            throw new AppCommonException(e.getMessage());
        }

        return vehicleHistoryResponse;
    }








/*    public Object getVehicleHistory(Integer vehicleId, String fromDate, String toDate) {
        try {
            return history_repo.getVehicleHistory(vehicleId, fromDate, toDate);
        }catch (Exception e){
            return e.getMessage();
        }

    }*/
}
