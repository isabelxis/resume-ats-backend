package br.com.isabelxis.resume_ats_backend.dto.resume;

import java.time.LocalDate;

public record ListResumeDTO(
    Long id,
    String title,
    String summary,
    String status,
    LocalDate createdAt
    
) {}
