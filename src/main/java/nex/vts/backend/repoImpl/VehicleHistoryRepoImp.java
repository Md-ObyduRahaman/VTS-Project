package nex.vts.backend.repoImpl;

import nex.vts.backend.exceptions.AppCommonException;
import nex.vts.backend.models.responses.HistoriesItem;
import nex.vts.backend.models.vehicle.Vehicle_History_Get;
import nex.vts.backend.models.vehicle.rowMapper.Vehicle_History_Get_RowMapper;
import nex.vts.backend.repositories.VehicleHistoryRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.math.BigInteger;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


@Repository
public class VehicleHistoryRepoImp implements VehicleHistoryRepo {

    private static Logger logger = LoggerFactory.getLogger(AddExpense_Imp.class);
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcCall jdbcCall;
    private DataSource dataSource;
    private SqlParameterSource parameterSource;

    @Autowired
    public VehicleHistoryRepoImp(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.dataSource = dataSource;
    }

    @Override
    public Object getVehicleHistoryForGpAndM2M(Integer vehicleId, Long fromDateTime, Long toDateTime, String schemaName) {

        try {

            jdbcTemplate.update("call ".concat(schemaName).concat("PROC_HIS_DATA_TD (?,?, ?,?)"), new Object[]{vehicleId, fromDateTime, toDateTime, 0});

            String query = "select ROWNUM                                 ROWNO,\n" +
                    "       ID,\n" +
                    "       VEHICLEID,\n" +
                    "       GROUPID,\n" +
                    "       HEAD,\n" +
                    "       to_char(time, 'DD-MM-YYYY HH24:MI:SS') TIME_STAMP,\n" +
                    "       LAT,\n" +
                    "       LONGS,\n" +
                    "       TIME_IN_NUMBER,\n" +
                    "       POSITION,\n" +
                    "       SPEED\n" +
                    "FROM (select ID,\n" +
                    "             VEHICLEID,\n" +
                    "             GROUPID,\n" +
                    "             HEAD,\n" +
                    "             TIME,\n" +
                    "             LAT,\n" +
                    "             LONGS,\n" +
                    "             TIME_IN_NUMBER,\n" +
                    "             POSITION,\n" +
                    "             SPEED\n" +
                    "      FROM ".concat(schemaName).concat("nex_historyrecv_gtt\n" +
                            "      where VEHICLEID = to_char(?)\n" +
                            "        and TIME_IN_NUMBER between ? and ?)\n" +
                            "order by time_in_number ASC");
            return jdbcTemplate.query(query, new RowMapper<HistoriesItem>() {
                @Override
                public HistoriesItem mapRow(ResultSet rs, int rowNum) throws SQLException {

                    return new HistoriesItem(

                            /*rs.getString("MAX_SPEED"),*/
                            rs.getInt("ROWNO"),
                            rs.getLong("ID"),
                            rs.getString("VEHICLEID"),
                            rs.getString("GROUPID"),
                            rs.getString("HEAD"),
                            rs.getString("TIME_STAMP"),
                            rs.getDouble("LAT"),
                            rs.getDouble("LONGS"),
                            rs.getLong("TIME_IN_NUMBER"),
                            rs.getString("POSITION"),
                            rs.getString("SPEED")

                    );
                }

            }, new Object[]{/*vehicleId,*/ vehicleId, fromDateTime, toDateTime});

        } catch (Exception e) {

            logger.error("Unexpected behaviour with param {}", vehicleId, fromDateTime, toDateTime);
            throw new AppCommonException(e.getMessage());

        }
    }
}
