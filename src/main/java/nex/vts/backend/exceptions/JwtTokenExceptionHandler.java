package nex.vts.backend.exceptions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import nex.vts.backend.models.responses.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;

@RestControllerAdvice
public class JwtTokenExceptionHandler {
    @Autowired
    private ObjectMapper objectMapper;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleSecurityException(Exception ex) throws JsonProcessingException {
        BaseResponse baseResponse = new BaseResponse();

        if (ex instanceof UsernameNotFoundException) {
            baseResponse.data=new ArrayList<>();
            baseResponse.apiName = null;
            baseResponse.status = false;
            baseResponse.errorCode = 403;
            baseResponse.errorMsg = ex.getMessage();

        } else if (ex instanceof AccessDeniedException) {
            baseResponse.data=new ArrayList<>();
            baseResponse.apiName = null;
            baseResponse.status = false;
            baseResponse.errorCode = 403;
            baseResponse.errorMsg = ex.getMessage();

        } else if (ex instanceof MalformedJwtException) {
            baseResponse.data=new ArrayList<>();
            baseResponse.apiName = null;
            baseResponse.status = false;
            baseResponse.errorCode = 403;
            baseResponse.errorMsg = "JWT signature is being tempered! Please input valid json-web-token";

        } else if (ex instanceof SignatureException) {
            baseResponse.data=new ArrayList<>();
            baseResponse.apiName = null;
            baseResponse.status = false;
            baseResponse.errorCode = 403;
            baseResponse.errorMsg = "JWT Signature not valid! Please input valid json-web-token";
        } else if (ex instanceof ExpiredJwtException) {
            baseResponse.data=new ArrayList<>();
            baseResponse.apiName = null;
            baseResponse.status = false;
            baseResponse.errorCode = 403;
            baseResponse.errorMsg = "JWT Token already expired !";
        } else if (ex.getMessage() != null) {
            baseResponse.data=new ArrayList<>();
            baseResponse.apiName = null;
            baseResponse.status = false;
            baseResponse.errorCode = 403;
            baseResponse.errorMsg = "Something went to wrong.Internal server Error! Please check Back-End Code";
            ex.printStackTrace();
        }
        String clientResponse = null;
        clientResponse = objectMapper.writeValueAsString(baseResponse);

        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        return ResponseEntity.status(HttpStatus.FORBIDDEN).headers(httpHeaders).body(clientResponse);

    }

}
