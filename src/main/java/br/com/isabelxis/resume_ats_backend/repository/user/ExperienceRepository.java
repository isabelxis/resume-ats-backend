package br.com.isabelxis.resume_ats_backend.repository.user;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.isabelxis.resume_ats_backend.entity.user.Experience;

public interface ExperienceRepository extends JpaRepository<Experience, Long> {
    Optional<Experience> findByIdAndUserEmail(Long id,String email); 
    List<Experience> findByIdIn(List<Long> id); 
    List<Experience> findByUserEmail(String email);

}
