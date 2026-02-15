package br.com.isabelxis.resume_ats_backend.infra.exception.auth;

public class InvalidPasswordException extends RuntimeException {
    public InvalidPasswordException() {
        super("Senha incorreta");
    }       
    
}
