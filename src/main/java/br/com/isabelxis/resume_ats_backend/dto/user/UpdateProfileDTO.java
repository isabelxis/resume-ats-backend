package br.com.isabelxis.resume_ats_backend.dto.user;

import jakarta.validation.constraints.Size;

public record UpdateProfileDTO(

        @Size(min = 2, max = 100, message = "O nome deve ter entre 2 e 100 caracteres"  )
        String name,
        String phone,
        String location,
        String linkedin,
        String github,
        String portfolio,
        String headline

) {}
