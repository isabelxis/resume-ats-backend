package br.com.isabelxis.resume_ats_backend.repository.resume;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.isabelxis.resume_ats_backend.entity.resume.Education;

public interface EducationRepository extends JpaRepository<Education, Long> {
    Optional<Education> findByIdAndResumeUserEmail(Long id, String email); 
    List<Education> findByResumeId(Long resumeId);
        
}
