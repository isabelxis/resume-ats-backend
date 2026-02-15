package br.com.isabelxis.resume_ats_backend.dto.resume;

public record CreateEducationDTO(
    String institution,
    String degree,
    String startDate,
    String endDate,
    String GPA,
    String skills
) {}
