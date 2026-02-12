package br.com.isabelxis.resume_ats_backend.repository.resume;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.isabelxis.resume_ats_backend.entity.resume.Skills;

public interface SkillsRepository extends JpaRepository<Skills, Long> {
    Optional<List<Skills>> findByResumeId(Long resumeId);
}
