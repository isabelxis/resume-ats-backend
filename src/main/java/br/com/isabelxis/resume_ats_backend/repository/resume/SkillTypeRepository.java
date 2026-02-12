package br.com.isabelxis.resume_ats_backend.repository.resume;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.isabelxis.resume_ats_backend.entity.resume.SkillType;

public interface SkillTypeRepository extends JpaRepository<SkillType, Long> {
    Optional<SkillType> findByNameIgnoreCase(String name);    
}
