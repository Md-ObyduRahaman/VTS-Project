package nex.vts.backend.repoImpl;

import nex.vts.backend.models.vehicle.rowMapper.Vehicle_History_Get_RowMapper;
import nex.vts.backend.repositories.Vehicle_History_Repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

@Repository
public class Vehicle_History_Repo_Imp implements Vehicle_History_Repo {
    private final JdbcTemplate jdbcTemplate;
    private SimpleJdbcCall jdbcCall;
    @Autowired
    private DataSource dataSource;
    private SqlParameterSource parameterSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Vehicle_History_Repo_Imp(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Object> getVehicleHistory(Integer vehicleId, String from_Date_Time, String to_Date_Time) {
        LocalDate localDate_new_fromDate = LocalDate.parse(from_Date_Time.substring(0, 8), DateTimeFormatter.ofPattern("yyyyMMdd")), localDate_new_toDate = LocalDate.parse(to_Date_Time.substring(0, 8), DateTimeFormatter.ofPattern("yyyyMMdd"));
        LocalTime localTime_new_fromTime = LocalTime.parse(from_Date_Time.substring(8), DateTimeFormatter.ofPattern("HHmmss")).plusHours(2), localTime_new_toTime = LocalTime.parse(to_Date_Time.substring(8), DateTimeFormatter.ofPattern("HHmmss")).plusHours(2);
        String fromTime, toTime, fromDateTime, toDateTime, from_Date, to_Date;
        if (localTime_new_fromTime.getMinute() == 0)
            fromTime = String.valueOf(localTime_new_fromTime).concat(":").concat("00");
        else fromTime = String.valueOf(localTime_new_fromTime);
        if (localTime_new_toTime.getMinute() == 0)
            toTime = String.valueOf(localTime_new_toTime).concat(":").concat("00");
        else toTime = String.valueOf(localTime_new_toTime);
        from_Date = String.valueOf(localDate_new_fromDate);
        to_Date = String.valueOf(localDate_new_toDate);
        fromDateTime = from_Date.replace("-", "").concat(fromTime.replace(":", ""));
        toDateTime = to_Date.replace("-", "").concat(toTime.replace(":", ""));
        execute_StoreProcedure(vehicleId, Long.parseLong(fromDateTime), Long.parseLong(toDateTime));
        String listQuery = "select GPSNEXGP.GET_MAX_CAR_SPEED(?)      as max_speed,\n       ROWNUM                                    ROWNO,\n       ID,\n       VEHICLEID                              as vehicle_id,\n       GROUPID                                as group_id,\n       DEVICEID                               as device_id,\n       to_char(time, 'DD-MM-YYYY HH24:MI:SS') as time_stamp,\n       LAT                                    as latitude,\n       LONGS                                  as longitude,\n       TIME_IN_NUMBER                         as date_time,\n       POSITION                               as engine_status,\n       SPEED\nFROM (select ID,\n             VEHICLEID,\n             GROUPID,\n             DEVICEID,\n             TIME,\n             LAT,\n             LONGS,\n             TIME_IN_NUMBER,\n             POSITION,\n             SPEED\n      FROM GPSNEXGP.nex_historyrecv_gtt\n      where VEHICLEID = to_char(?))\nwhere TIME_IN_NUMBER between ? and ?\norder by time_in_number ASC";
        List<Object> trackList = Collections.singletonList(jdbcTemplate.query(listQuery, new Vehicle_History_Get_RowMapper(), vehicleId, vehicleId, Long.parseLong(fromDateTime), Long.parseLong(toDateTime)));
        return trackList;
    }

    public int execute_StoreProcedure(Integer vehicleId, Long bigInteger_fromDateTime, Long bigInteger_toDateTime) {
        jdbcCall = new SimpleJdbcCall(dataSource).withSchemaName("GPSNEXGP").withProcedureName("PROC_HIS_DATA_TD");
        parameterSource = new MapSqlParameterSource().addValue("p_vehicleid", vehicleId).addValue("p_date_from", bigInteger_fromDateTime).addValue("p_date_to", bigInteger_toDateTime).addValue("p_interval", 0);
        jdbcCall.execute(parameterSource);
        return 1;
    }
}
