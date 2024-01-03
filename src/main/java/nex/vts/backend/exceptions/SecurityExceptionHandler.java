package nex.vts.backend.exceptions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nex.vts.backend.models.responses.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class SecurityExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    ObjectMapper objectMapper;

    //https://docs.spring.io/spring-security/site/docs/4.2.4.RELEASE/apidocs/org/springframework/security/core/AuthenticationException.html
    //TODO: try to send actual message based on sub exceptions
    @ExceptionHandler({AuthenticationException.class})
    @ResponseBody
    public ResponseEntity<String> handleAuthenticationException(Exception ex) throws JsonProcessingException {

        BaseResponse baseResponse = new BaseResponse();
        baseResponse.status = false;
        baseResponse.errorCode = 401;
        baseResponse.errorMsg = "Something went to wrong.Internal server Error! Please check Back-End Code";
        String clientResponse = objectMapper.writeValueAsString(baseResponse);

        System.out.println("Exception name " + ex.getClass());
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).headers(httpHeaders).body(clientResponse);
    }
}
