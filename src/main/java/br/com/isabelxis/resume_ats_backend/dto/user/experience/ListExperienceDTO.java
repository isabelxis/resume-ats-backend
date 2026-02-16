package br.com.isabelxis.resume_ats_backend.dto.user.experience;

import java.time.LocalDate;

public record ListExperienceDTO(
    Long id,
    String company,
    String position,
    String description,
    LocalDate startDate,
    LocalDate endDate,
    String skills,
    String models,
    Boolean current,
    String location,
    String employmentType
) {
    
}
