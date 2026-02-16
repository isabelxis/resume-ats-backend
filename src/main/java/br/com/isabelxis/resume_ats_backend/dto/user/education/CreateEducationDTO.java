package br.com.isabelxis.resume_ats_backend.dto.user.education;

import java.time.LocalDate;

public record CreateEducationDTO(
    String institution,
    String degree,
    LocalDate startDate,
    LocalDate endDate,
    String GPA,
    String skills
) {}
