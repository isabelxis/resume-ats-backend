package br.com.isabelxis.resume_ats_backend.repository.resume;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.isabelxis.resume_ats_backend.entity.resume.Experience;

public interface ExperienceRepository extends JpaRepository<Experience, Long> {

    List<Experience> findByResumeIdAndResumeUserEmailOrderByDisplayOrderDesc( Long resumeId, String email);
    Optional<Experience> findByIdAndResumeUserEmail(Long id, String email); 
    List<Experience> findByResumeId(Long resumeId);   
}
