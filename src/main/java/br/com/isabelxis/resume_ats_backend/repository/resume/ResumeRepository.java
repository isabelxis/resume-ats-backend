package br.com.isabelxis.resume_ats_backend.repository.resume;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.isabelxis.resume_ats_backend.entity.resume.Resume;


public interface ResumeRepository extends JpaRepository<Resume, Long> {
    Optional<Resume> findByIdAndUserEmail(Long id, String email);
    List<Resume> findByUserEmailOrderByCreatedAtDesc(String email);
    
}
