package br.com.isabelxis.resume_ats_backend.dto.user;

public record AuthResponseDTO(
        String token,
        UserResponseDTO user
) {
}