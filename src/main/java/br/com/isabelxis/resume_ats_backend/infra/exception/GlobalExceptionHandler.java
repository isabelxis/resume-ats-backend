package br.com.isabelxis.resume_ats_backend.infra.exception;

import java.time.Instant;
import java.util.Map;

import org.apache.catalina.connector.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<?> handleEmailAlreadyExistsException(EmailAlreadyExistsException ex) {
        return ResponseEntity
            .status(Response.SC_CONFLICT)//409
            .body(Map.of(
                "timestamp", Instant.now(),
                "status", 409,
                "error", ex.getMessage()
            ));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleUserNotFoundException(UserNotFoundException ex) {
        return ResponseEntity
            .status(Response.SC_NOT_FOUND)//404
            .body(Map.of(
                "timestamp", Instant.now(),
                "status", 404,
                "error", ex.getMessage()
            ));
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<?> handleInvalidPasswordException(InvalidPasswordException ex) {  
        return ResponseEntity
            .status(Response.SC_UNAUTHORIZED)//401
            .body(Map.of(
                "timestamp", Instant.now(),
                "status", 401,
                "error", ex.getMessage()
            ));
    }    
    
}
