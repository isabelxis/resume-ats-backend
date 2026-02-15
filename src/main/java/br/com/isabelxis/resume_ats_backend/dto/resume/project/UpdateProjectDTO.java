package br.com.isabelxis.resume_ats_backend.dto.resume.project;

public record UpdateProjectDTO(
    String name,
    String description,
    String link,
    String technologies
) {}
