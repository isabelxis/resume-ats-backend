package br.com.isabelxis.resume_ats_backend.repository.user;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.isabelxis.resume_ats_backend.entity.user.Skill;

public interface SkillRepository extends JpaRepository<Skill, Long> {
    Optional<Skill> findByIdAndUserEmail(Long id,String email); 
    List<Skill> findByIdIn(List<Long> id); 
    List<Skill> findByUserEmail(String email);
    
}
