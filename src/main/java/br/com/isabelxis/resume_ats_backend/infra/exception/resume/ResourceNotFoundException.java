package br.com.isabelxis.resume_ats_backend.infra.exception.resume;

public class ResourceNotFoundException extends RuntimeException {
    
    public ResourceNotFoundException(String resource) {
        super(resource + " não encontrado ou acesso negado");
    }
    
}
