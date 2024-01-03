package nex.vts.backend.utilities;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class UtilityMethods {
    public static Boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }
    public static String getCurrentDateTime() {

        ZoneId dhaka = ZoneId.of("Asia/Dhaka");
        ZonedDateTime dhakaTime = ZonedDateTime.now(dhaka);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return dhakaTime.format(formatter);
    }

    public static Long obfuscateId(String strUserId) {
        try {
            Long User_id = Long.parseLong(strUserId);
            Long firstId = User_id * 12 * 13 * 17;

            Long secondId = 17L * 18L * 19L;

            Long finalId = firstId + secondId;
            return finalId;
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Couldn't convert to Long.");
            return null;
        }
    }

    public static Long deObfuscateId(Long finalId) {
        Long secondId = 17L * 18L * 19L, newlId = finalId - secondId;
        Long result = newlId / (13 * 12 * 17);
        return result;
    }
}
