package nex.vts.backend.repoImpl;

import nex.vts.backend.models.vehicle.rowMapper.Vehicle_History_Get_RowMapper;
import nex.vts.backend.repositories.Vehicle_History_Repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
    public List<Object> getVehicleHistory(Integer vehicleId, String fromDate, String toDate) {
        String new_fromDate = fromDate.substring(0, 8),
                new_toDate = toDate.substring(0, 8),
                new_fromTime = fromDate.substring(8),
                new_toTime = toDate.substring(8);
        LocalDate localDate_new_fromDate = LocalDate.parse(new_fromDate, DateTimeFormatter.ofPattern("yyyyMMdd")),
                localDate_new_toDate = LocalDate.parse(new_toDate, DateTimeFormatter.ofPattern("yyyyMMdd"));
        LocalTime localTime_new_fromTime = LocalTime.parse(new_fromTime, DateTimeFormatter.ofPattern("HHmmss")),
                localTime_new_toTime = LocalTime.parse(new_toTime, DateTimeFormatter.ofPattern("HHmmss")),
                plus_localTime_new_fromTime = localTime_new_fromTime.plusHours(2),
                plus_localTime_new_toTime = localTime_new_toTime.plusHours(2);
        String string_localDate_new_fromDate,string_localDate_new_toDate,string_plus_localTime_new_fromTime,string_plus_localTime_new_toTime;
        String from_string,from_string1,from_string2,to_string,to_string1,to_string2,fromTime,toTime,fromDateTime,toDateTime,from_Date,to_Date;
        Long bigInteger_fromDateTime,bigInteger_toDateTime;

        if (plus_localTime_new_fromTime.getMinute() == 0){
            from_string = String.valueOf(plus_localTime_new_fromTime);
            from_string1 = from_string.concat(":");
            from_string2 = from_string1.concat("00");
            fromTime = from_string2;
        }
        else
            fromTime = String.valueOf(plus_localTime_new_fromTime);

        if (plus_localTime_new_toTime.getMinute() == 0){
            to_string = String.valueOf(plus_localTime_new_toTime);
            to_string1 = to_string.concat(":");
            to_string2 = to_string1.concat("00");
            toTime = to_string2;
        }
        else
            toTime = String.valueOf(plus_localTime_new_toTime);

        from_Date = String.valueOf(localDate_new_fromDate);
        to_Date = String.valueOf(localDate_new_toDate);

        fromDateTime = from_Date.replace("-","").concat(fromTime.replace(":",""));
        toDateTime = to_Date.replace("-","").concat(toTime.replace(":",""));

        if (plus_localTime_new_fromTime.getHour() == 0) localDate_new_fromDate = localDate_new_fromDate.plusDays(1);
        if (plus_localTime_new_toTime.getHour() == 0) localDate_new_toDate = localDate_new_toDate.plusDays(1);

         bigInteger_fromDateTime = Long.parseLong(fromDateTime);
         bigInteger_toDateTime = Long.parseLong(toDateTime);

         execute_StoreProcedure( vehicleId, bigInteger_fromDateTime,bigInteger_toDateTime);

        String listQuery = "select GPSNEXGP.GET_MAX_CAR_SPEED(?)      as max_speed,\n" +
                "       ROWNUM                                    ROWNO,\n" +
                "       ID,\n" +
                "       VEHICLEID                              as vehicle_id,\n" +
                "       GROUPID                                as group_id,\n" +
                "       DEVICEID                               as device_id,\n" +
                "       to_char(time, 'DD-MM-YYYY HH24:MI:SS') as time_stamp,\n" +
                "       LAT                                    as latitude,\n" +
                "       LONGS                                  as longitude,\n" +
                "       TIME_IN_NUMBER                         as date_time,\n" +
                "       POSITION                               as engine_status,\n" +
                "       SPEED\n" +
                "FROM (select ID,\n" +
                "             VEHICLEID,\n" +
                "             GROUPID,\n" +
                "             DEVICEID,\n" +
                "             TIME,\n" +
                "             LAT,\n" +
                "             LONGS,\n" +
                "             TIME_IN_NUMBER,\n" +
                "             POSITION,\n" +
                "             SPEED\n" +
                "      FROM GPSNEXGP.nex_historyrecv_gtt\n" +
                "      where VEHICLEID = to_char(?))\n" +
                "where TIME_IN_NUMBER between ? and ?\n" +
                "order by time_in_number ASC";
        List<Object> trackList = Collections.singletonList(jdbcTemplate.query(listQuery, new Vehicle_History_Get_RowMapper(),vehicleId,vehicleId,bigInteger_fromDateTime,bigInteger_toDateTime));
        return trackList;
    }


    public int execute_StoreProcedure(Integer vehicleId, Long bigInteger_fromDateTime, Long bigInteger_toDateTime) {
        jdbcCall = new SimpleJdbcCall(dataSource).withSchemaName("GPSNEXGP").withProcedureName("PROC_HIS_DATA_TD");
        parameterSource = new MapSqlParameterSource().addValue("p_vehicleid", vehicleId).addValue("p_date_from", bigInteger_fromDateTime).addValue("p_date_to", bigInteger_toDateTime).addValue("p_interval", 0);
        jdbcCall.execute(parameterSource);
        return 1;
    }
}
