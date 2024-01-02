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

@RestControllerAdvice
public class JwtTokenExceptionHandler {
    @Autowired
    private ObjectMapper objectMapper;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleSecurityException(Exception ex) throws JsonProcessingException {
        BaseResponse baseResponse = new BaseResponse();

        if (ex instanceof UsernameNotFoundException) {
            baseResponse.apiName = null;
            baseResponse.status = false;
            baseResponse.errorCode = 403;
            baseResponse.errorMsg = ex.getMessage();

        }
        if (ex instanceof AccessDeniedException) {
            baseResponse.apiName = null;
            baseResponse.status = false;
            baseResponse.errorCode = 403;
            baseResponse.errorMsg = ex.getMessage();

        }
        if (ex instanceof MalformedJwtException) {
            baseResponse.apiName = null;
            baseResponse.status = false;
            baseResponse.errorCode = 403;
            baseResponse.errorMsg = "JWT signature is being tempered! Please input valid json-web-token";

        }

        if (ex instanceof SignatureException) {
            baseResponse.apiName = null;
            baseResponse.status = false;
            baseResponse.errorCode = 403;
            baseResponse.errorMsg = "JWT Signature not valid! Please input valid json-web-token";
        }
        if (ex instanceof ExpiredJwtException) {
            baseResponse.apiName = null;
            baseResponse.status = false;
            baseResponse.errorCode = 403;
            baseResponse.errorMsg = "JWT Token already expired !";
        }
        String clientResponse = null;
        clientResponse = objectMapper.writeValueAsString(baseResponse);

        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        return ResponseEntity.status(HttpStatus.FORBIDDEN).headers(httpHeaders).body(clientResponse);

    }

}
