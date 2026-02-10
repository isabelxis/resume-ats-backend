package br.com.isabelxis.resume_ats_backend.dto.user;

public record RegisterRequestDTO(
        String email,
        String password
) {
}