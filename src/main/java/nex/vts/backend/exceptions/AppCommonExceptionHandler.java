package nex.vts.backend.exceptions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nex.vts.backend.models.responses.BaseResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AppCommonExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(AppCommonExceptionHandler.class);

    @Autowired
    private ObjectMapper objectMapper;

    /*
    Common Exception Message Format
    {api_version}##{api_name}##{error_code}##{error_message}
    */

    @ExceptionHandler(value = {AppCommonException.class})
    public ResponseEntity<String> transparentApiErrorException(AppCommonException ex) {

        System.out.println("Controller advice " + ex.getMessage());
        String[] errorMessages = ex.getMessage().split("##");
        int errorCode = errorMessages[0] != null ? Integer.parseInt(errorMessages[0]) : 0;
        String errorMsg = errorMessages[1] != null ? errorMessages[1] : "";

        BaseResponse baseResponse = new BaseResponse();
        baseResponse.status = false;
        baseResponse.errorCode = errorCode;
        baseResponse.errorMsg = errorMsg;

        String clientResponse = null;

        try {
            clientResponse = objectMapper.writeValueAsString(baseResponse);
        } catch (JsonProcessingException e) {
            logger.error("Exception caught when sending error message", e);
        }

        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(clientResponse);
    }

}
