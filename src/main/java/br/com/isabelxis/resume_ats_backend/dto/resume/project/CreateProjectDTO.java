package br.com.isabelxis.resume_ats_backend.dto.resume.project;

public record CreateProjectDTO(
    String name,
    String description,
    String link,
    String technologies
) {}
