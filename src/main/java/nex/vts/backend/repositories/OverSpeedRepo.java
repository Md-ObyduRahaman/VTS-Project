package nex.vts.backend.repositories;

import nex.vts.backend.models.responses.OverSpeedData;

import java.util.ArrayList;
import java.util.Optional;

public interface OverSpeedRepo {


    OverSpeedData getOverSpeedInfo(String p_alert_type, String p_report_type, int p_profile_type,
                                                        Long p_profile_id,
                                                        Long p_profile_p_id,
                                                        int p_all_vehicle_flag,
                                                        Long p_vehicle_id,
                                                        String p_date_from,
                                                        String p_date_to,
                                                        int deviceType
    );

}
