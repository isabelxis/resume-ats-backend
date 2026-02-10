package br.com.isabelxis.resume_ats_backend.dto.user;

public record LoginRequestDTO(
        String email,
        String password
) {
}