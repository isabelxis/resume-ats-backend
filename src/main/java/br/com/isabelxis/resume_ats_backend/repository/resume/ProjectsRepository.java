package br.com.isabelxis.resume_ats_backend.repository.resume;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.isabelxis.resume_ats_backend.entity.resume.Project;

public interface ProjectsRepository extends JpaRepository<Project, Long> {
        
    Optional<Project> findByIdAndResumeUserEmail(Long id, String email); 
    List<Project> findByResumeId(Long resumeId); 

}
