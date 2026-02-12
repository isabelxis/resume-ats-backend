package br.com.isabelxis.resume_ats_backend.repository.resume;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.isabelxis.resume_ats_backend.entity.resume.Education;

public interface EducationRepository extends JpaRepository<Education, Long> {

        
}
