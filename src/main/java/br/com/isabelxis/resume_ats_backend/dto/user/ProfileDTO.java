package br.com.isabelxis.resume_ats_backend.dto.user;

public record ProfileDTO(
        String name,
        String email,
        String phone,
        String location,
        String linkedin,
        String github,
        String portfolio,
        String headline
) {}
