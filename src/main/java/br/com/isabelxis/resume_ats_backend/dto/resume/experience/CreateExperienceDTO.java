package br.com.isabelxis.resume_ats_backend.dto.resume.experience;

public record CreateExperienceDTO(
    String company,
    String position,
    String description,
    String startDate,
    String endDate,
    String skills,
    String models,
    Boolean current
) {
    
}
