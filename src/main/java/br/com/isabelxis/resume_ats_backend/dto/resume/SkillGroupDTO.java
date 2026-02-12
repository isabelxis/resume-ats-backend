package br.com.isabelxis.resume_ats_backend.dto.resume;

import java.util.List;

public record SkillGroupDTO(
    String type,
    List<String> skills
) {}
