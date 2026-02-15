package br.com.isabelxis.resume_ats_backend.dto.resume;

public record ListEducationDTO(
    Long id,
    String institution,
    String degree,
    String startDate,
    String endDate,
    String GPA,
    String skills
) {}
