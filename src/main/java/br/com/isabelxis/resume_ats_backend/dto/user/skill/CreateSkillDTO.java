package br.com.isabelxis.resume_ats_backend.dto.user.skill;

import br.com.isabelxis.resume_ats_backend.entity.user.SkillCategory;

public record CreateSkillDTO(
    String name,
    SkillCategory category
) {
    
}
