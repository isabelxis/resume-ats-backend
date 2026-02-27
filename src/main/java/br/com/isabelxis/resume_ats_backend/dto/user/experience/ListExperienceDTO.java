package br.com.isabelxis.resume_ats_backend.dto.user.experience;

import java.time.LocalDate;

import br.com.isabelxis.resume_ats_backend.entity.user.ExperienceModel;

public record ListExperienceDTO(
    Long id,
    String company,
    String position,
    String description,
    LocalDate startDate,
    LocalDate endDate,
    String skills,
    ExperienceModel models,
    Boolean current,
    String location,
    String employmentType
) {
    
}
