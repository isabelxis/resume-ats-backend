package br.com.isabelxis.resume_ats_backend.dto.user.project;

public record ListProjectDTO(
    Long id,
    String name,
    String description,
    String link,
    String technologies
) { 
}
