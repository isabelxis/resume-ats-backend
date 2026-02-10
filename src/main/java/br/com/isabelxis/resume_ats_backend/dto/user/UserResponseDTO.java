package br.com.isabelxis.resume_ats_backend.dto.user;

public record UserResponseDTO(
        Long id,
        String email,
        String plan
) {
}