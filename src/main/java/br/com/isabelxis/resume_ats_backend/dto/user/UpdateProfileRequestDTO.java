package br.com.isabelxis.resume_ats_backend.dto.user;

import jakarta.validation.constraints.Size;

public record UpdateProfileRequestDTO(

        @Size(min = 2, max = 100, message = "O nome deve ter entre 2 e 100 caracteres"  )
        String name,

        @Size(min = 8, message = "A senha deve ter no mínimo 8 caracteres")
        String password
) {}
