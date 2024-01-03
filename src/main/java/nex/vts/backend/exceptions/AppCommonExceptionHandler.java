package nex.vts.backend.exceptions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nex.vts.backend.models.responses.BaseResponse;
import nex.vts.backend.utilities.AESEncryptionDecryption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;

@RestControllerAdvice
public class AppCommonExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(AppCommonExceptionHandler.class);

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private Environment environment;
    /*
    Common Exception Message Format
    {api_version}##{api_name}##{error_code}##{error_message}##{deviceType}##{apiVersion}
    */

    @ExceptionHandler(value = {AppCommonException.class})
    @ResponseBody
    public ResponseEntity<String> transparentApiErrorException(AppCommonException ex) throws JsonProcessingException{

        System.out.println("Controller advice " + ex.getMessage());
        String[] errorMessages = ex.getMessage().split("##");
        int errorCode = errorMessages[0] != null ? Integer.parseInt(errorMessages[0]) : 0;
        String errorMsg = errorMessages[1] != null ? errorMessages[1] : "";
        int deviceType = errorMessages[2] != null ? Integer.parseInt(errorMessages[2]) : 0;
        int apiVersion = errorMessages[3] != null ? Integer.parseInt(errorMessages[3]) : 0;

        BaseResponse baseResponse = new BaseResponse();
        baseResponse.data=new ArrayList<>();
        baseResponse.status = false;
        baseResponse.errorCode = errorCode;
        baseResponse.errorMsg = errorMsg;

        // Determine the HttpStatus dynamically based on the errorCode or any other criteria
        HttpStatus httpStatus;

        switch (errorCode) {
            case 200:
                httpStatus = HttpStatus.OK;
                break;
            case 400:
                httpStatus = HttpStatus.BAD_REQUEST;
                break;
            case 404:
                httpStatus = HttpStatus.NOT_FOUND;
                break;
            // Add more cases as needed based on your error codes
            default:
                httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
                break;
        }

        String clientResponse = null;

        String appActiveProfile = environment.getProperty("spring.profiles.active");
        AESEncryptionDecryption aesCrypto = new AESEncryptionDecryption(appActiveProfile,deviceType,apiVersion);

        try {
            clientResponse = objectMapper.writeValueAsString(baseResponse);
        } catch (JsonProcessingException e) {
            logger.error("Exception caught when sending error message", e);
        }

        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

       // return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(aesCrypto.aesEncrypt(clientResponse,apiVersion));
        return ResponseEntity.status(httpStatus).headers(httpHeaders).body(clientResponse);
    }

}
