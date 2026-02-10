package br.com.isabelxis.resume_ats_backend.infra.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("Usuário não encontrado");
    }
    
}
