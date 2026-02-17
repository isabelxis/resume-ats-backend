package br.com.isabelxis.resume_ats_backend.repository.user;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.isabelxis.resume_ats_backend.entity.user.Project;

public interface ProjectsRepository extends JpaRepository<Project, Long> {  
    Optional<Project> findByIdAndUserEmail(Long id,String email); 
    List<Project> findByIdIn(List<Long> id);  
    List<Project> findByUserEmail(String email);

}
