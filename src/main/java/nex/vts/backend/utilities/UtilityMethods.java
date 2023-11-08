package nex.vts.backend.utilities;

public class UtilityMethods {
    public static Boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    public static Long obfuscateId(String strUserId) {
        try {
            Long User_id = Long.parseLong(strUserId);
            System.out.println("Converted Long value: " + User_id);
            Long firstId = User_id * 2 * 3 * 7;
            System.out.println("firstId Value: " + firstId);
            Long secondId = 7L * 8L * 9L;
            System.out.println("secondId Value: " + secondId);
            Long finalId = firstId + secondId;
            System.out.println("finalId Value: " + finalId);
            return finalId;
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Couldn't convert to Long.");
            return null;
        }
    }

    public static Long deObfuscateId(Long finalId) {
        Long secondId = 7L * 8L * 9L, newlId = finalId - secondId;
        System.out.println("finalId Value: " + newlId);
        Long result = newlId / (3 * 2 * 7);
        System.out.println("result Value: " + result);
        return result;
    }
}
