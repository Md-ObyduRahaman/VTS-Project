package nex.vts.backend.repoImpl;

import nex.vts.backend.repositories.Vehicle_History_Repo;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Repository
public class Vehicle_History_Repo_Imp implements Vehicle_History_Repo {
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcCall simpleJdbcCall;
    private SqlParameterSource parameterSource;
    private DataSource dataSource;

    public Vehicle_History_Repo_Imp(JdbcTemplate jdbcTemplate, SimpleJdbcCall simpleJdbcCall, SqlParameterSource parameterSource, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcCall = simpleJdbcCall;
        this.parameterSource = parameterSource;
        this.dataSource = dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Object> getVehicleHistory(Integer vehicleId, String fromDate, String toDate) {
        String new_fromDate = fromDate.substring(0, 8);
        String new_toDate = toDate.substring(0, 8);
        String new_fromTime = fromDate.substring(8);
        String new_toTime = toDate.substring(8);
     /*   LocalDate plus_localDate_new_fromDate, plus_localDate_new_toDate;*/
        LocalDate localDate_new_fromDate = LocalDate.parse(new_fromDate, DateTimeFormatter.ofPattern("yyyyMMdd"));
        LocalDate localDate_new_toDate = LocalDate.parse(new_toDate, DateTimeFormatter.ofPattern("yyyyMMdd"));
        LocalTime localTime_new_fromTime = LocalTime.parse(new_fromTime, DateTimeFormatter.ofPattern("HHmmss"));
        LocalTime localTime_new_toTime = LocalTime.parse(new_toTime, DateTimeFormatter.ofPattern("HHmmss"));
        LocalTime plus_localTime_new_fromTime = localTime_new_fromTime.plusHours(2);
        LocalTime plus_localTime_new_toTime = localTime_new_toTime.plusHours(2);
        if (plus_localTime_new_fromTime.getHour() == 0) {
            localDate_new_fromDate = localDate_new_fromDate.plusDays(1);
        }
        if (plus_localTime_new_toTime.getHour() == 0) {
            localDate_new_toDate = localDate_new_toDate.plusDays(1);
        }
        String string_localDate_new_fromDate = String.valueOf(localDate_new_fromDate), string_localDate_new_toDate = String.valueOf(localDate_new_toDate), string_plus_localTime_new_fromTime = String.valueOf(plus_localTime_new_fromTime), string_plus_localTime_new_toTime = String.valueOf(plus_localTime_new_toTime), fromDateTime = string_localDate_new_fromDate.replace("-", "").concat(string_plus_localTime_new_fromTime.replace(":", "")), toDateTime = string_plus_localTime_new_toTime.replace("-", "").concat(string_plus_localTime_new_toTime.replace(":", ""));
        BigInteger bigInteger_fromDateTime = new BigInteger(fromDateTime), bigInteger_toDateTime = new BigInteger(toDateTime);
        simpleJdbcCall = new SimpleJdbcCall(dataSource).withCatalogName("GPSNEXGP").withProcedureName("PROC_HIS_DATA_TD");
        parameterSource = new MapSqlParameterSource().addValue("p_vehicleid", vehicleId).addValue("p_date_from", bigInteger_fromDateTime).addValue("p_date_to", bigInteger_toDateTime).addValue("p_interval", 0);
        simpleJdbcCall.execute(parameterSource);
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


        return null;
    }
}
