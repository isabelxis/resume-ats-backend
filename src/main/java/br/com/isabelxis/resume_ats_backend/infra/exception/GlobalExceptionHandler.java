package br.com.isabelxis.resume_ats_backend.infra.exception;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
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
                "message", ex.getMessage()
            ));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleUserNotFoundException(UserNotFoundException ex) {
        return ResponseEntity
            .status(Response.SC_NOT_FOUND)//404
            .body(Map.of(
                "timestamp", Instant.now(),
                "status", 404,
                "message", ex.getMessage()
            ));
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<?> handleInvalidPasswordException(InvalidPasswordException ex) {  
        return ResponseEntity
            .status(Response.SC_UNAUTHORIZED)//401
            .body(Map.of(
                "timestamp", Instant.now(),
                "status", 401,
                "message", ex.getMessage()
            ));
    }    

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationErrors(
            MethodArgumentNotValidException ex
    ) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult()
          .getFieldErrors()
          .forEach(error ->
              errors.put(error.getField(), error.getDefaultMessage())
          );

        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(Map.of(
                "status", 400,
                "errors", errors
            ));
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneric(Exception ex) {
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(Map.of(
                "status", 500,
                "message", "Erro interno no servidor"
            ));
    }

    @ExceptionHandler(InvalidResetTokenException.class)
    public ResponseEntity<?> handleInvalidResetToken(InvalidResetTokenException ex) {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(Map.of(
            "status", 400,
            "message", ex.getMessage()
        ));
    }
}
