package br.com.isabelxis.resume_ats_backend.dto.user;

public record ProfileDTO(
        Long id,
        String name,
        String email,
        String plan,
        String phone,
        String location,
        String linkedin,
        String github,
        String portfolio,
        String headline
) {}
