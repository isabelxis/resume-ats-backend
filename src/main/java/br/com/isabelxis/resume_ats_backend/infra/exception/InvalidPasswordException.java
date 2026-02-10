package br.com.isabelxis.resume_ats_backend.infra.exception;

public class InvalidPasswordException extends RuntimeException {
    public InvalidPasswordException() {
        super("Senha inválida");
    }       
    
}
