package br.com.isabelxis.resume_ats_backend.dto.user.project;

public record UpdateProjectDTO(
    String name,
    String description,
    String link,
    String technologies
) {}
