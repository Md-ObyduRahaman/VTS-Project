package nex.vts.backend.utilities;

import nex.vts.backend.exceptions.AppCommonException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;


public class AESEncryptionDecryption {

    private Integer deviceType;


        private String API_V1_DEVICE_1_SECRET_KEY = "Nx123OTT0908J129";
        private String API_V1_DEVICE_2_SECRET_KEY = "Nx123OTT0000J129";
        private String API_V1_DEVICE_3_SECRET_KEY = "Nx123OTT0876J129";
        private String API_V2_DEVICE_1_SECRET_KEY = "Nx123OTT0456J129";
        private String API_V2_DEVICE_2_SECRET_KEY = "Nx123OTT0123J129";
        private String API_V2_DEVICE_3_SECRET_KEY = "Nx123OTT0098J129";

    private final Logger logger = LoggerFactory.getLogger(AESEncryptionDecryption.class);
    private SecretKeySpec keySpecV1;
    private SecretKeySpec keySpecV2;

    public AESEncryptionDecryption(String applicationDomain, int deviceType, int apiVersion) {
        this.deviceType=deviceType;
        if(applicationDomain.equalsIgnoreCase("GP")){
            API_V1_DEVICE_1_SECRET_KEY = "Nx123OTT0908J129";
            API_V1_DEVICE_2_SECRET_KEY = "Nx123OTT0000J129";
            API_V1_DEVICE_3_SECRET_KEY = "Nx123OTT0876J129";
            API_V2_DEVICE_1_SECRET_KEY = "Nx123OTT0456J129";
            API_V2_DEVICE_2_SECRET_KEY = "Nx123OTT0123J129";
            API_V2_DEVICE_3_SECRET_KEY = "Nx123OTT0098J129";
        }else if(applicationDomain.equalsIgnoreCase("M2M")){
            API_V1_DEVICE_1_SECRET_KEY = "Nx123OTT0908J129";
            API_V1_DEVICE_2_SECRET_KEY = "Nx123OTT0000J129";
            API_V1_DEVICE_3_SECRET_KEY = "Nx123OTT0876J129";
            API_V2_DEVICE_1_SECRET_KEY = "Nx123OTT0456J129";
            API_V2_DEVICE_2_SECRET_KEY = "Nx123OTT0123J129";
            API_V2_DEVICE_3_SECRET_KEY = "Nx123OTT0098J129";
        }

        if (apiVersion == 1) {
            if (deviceType == 1) {
                keySpecV1 = new SecretKeySpec(API_V1_DEVICE_1_SECRET_KEY.getBytes(), "AES");
            } else if (deviceType == 2) {
                keySpecV1 = new SecretKeySpec(API_V1_DEVICE_2_SECRET_KEY.getBytes(), "AES");
            } else if (deviceType == 3) {
                keySpecV1 = new SecretKeySpec(API_V1_DEVICE_3_SECRET_KEY.getBytes(), "AES");
            }
        } else if (apiVersion == 2) {
            if (deviceType == 1) {
                keySpecV2 = new SecretKeySpec(API_V2_DEVICE_1_SECRET_KEY.getBytes(), "AES");
            } else if (deviceType == 2) {
                keySpecV2 = new SecretKeySpec(API_V2_DEVICE_2_SECRET_KEY.getBytes(), "AES");
            } else if (deviceType == 3) {
                keySpecV2 = new SecretKeySpec(API_V2_DEVICE_3_SECRET_KEY.getBytes(), "AES");
            }
        }

    }


    // return base64
    public String aesEncrypt(String jsonRequest, int apiVersion) {

        String base64 = null;
        try {
            byte[] cipher = encrypt(jsonRequest.getBytes(), apiVersion);
            base64 = new String(Base64.getEncoder().encode(cipher), StandardCharsets.UTF_8);
        } catch (Exception e) {
            logger.error("While encrypting : ", e);
            throw new AppCommonException(400 + "##Could not prepare API response. Encryption issue occurred##" + deviceType + "##" + apiVersion);

        }
        return base64;
    }

    // return plain text
    public String aesDecrypt(String encryptedData, int apiVersion) {

        String responseString;
        // replacing all new line
        encryptedData = encryptedData.replace("\n", "");

        try {
            String modifiedEncryptedData1 = encryptedData.replace(" ", "+");
            responseString = new String(decrypt(Base64.getDecoder().decode(modifiedEncryptedData1), apiVersion), StandardCharsets.UTF_8);
            return responseString;
        } catch (Exception e) {
            // modify again request body
            try {
                String modifiedEncryptedData2 = encryptedData.replace(" ", "");
                responseString = new String(decrypt(Base64.getDecoder().decode(modifiedEncryptedData2), apiVersion), StandardCharsets.UTF_8);
                return responseString;
            } catch (Exception f) {
                logger.error("Could not decrypt", f);
                throw new AppCommonException(400 + "##Malformed request. Decryption issue occurred##" + deviceType + "##" + apiVersion);

            }
        }
    }

    private byte[] encrypt(byte[] data, int apiVersion) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        if (apiVersion == 1) {
            cipher.init(Cipher.ENCRYPT_MODE, keySpecV1);
        } else if (apiVersion == 2) {
            cipher.init(Cipher.ENCRYPT_MODE, keySpecV2);
        }
        byte[] fdata = cipher.doFinal(data);
        return fdata;
    }

    private byte[] decrypt(byte[] data, int apiVersion) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        if (apiVersion == 1) {
            cipher.init(Cipher.DECRYPT_MODE, keySpecV1);
        } else if (apiVersion == 2) {
            cipher.init(Cipher.DECRYPT_MODE, keySpecV2);
        }
        return cipher.doFinal(data);
    }

}
