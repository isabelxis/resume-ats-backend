package br.com.isabelxis.resume_ats_backend.dto.auth;

import br.com.isabelxis.resume_ats_backend.dto.user.UserResponseDTO;

public record AuthResponseDTO(
        String token,
        UserResponseDTO user
) {
}