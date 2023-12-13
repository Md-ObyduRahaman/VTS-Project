package nex.vts.backend.utilities;


import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.math.BigInteger;

@Service
@NoArgsConstructor
public class PasswordHashUtility {
    public static String generateSHA256Hash(String input) {

        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = messageDigest.digest(input.getBytes());
            BigInteger bigInt = new BigInteger(1, hashBytes);
            String hash = bigInt.toString(16);

            // Pad with leading zeros if needed
            while (hash.length() < 64) {
                hash = "0" + hash;
            }

            return hash;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}

