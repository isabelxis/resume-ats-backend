package br.com.isabelxis.resume_ats_backend.infra.exception.auth;

public class InvalidResetTokenException extends RuntimeException {
    public InvalidResetTokenException(String message) {
        super(message);
    }
    
}
