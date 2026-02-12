package br.com.isabelxis.resume_ats_backend.repository.resume;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.isabelxis.resume_ats_backend.entity.resume.Projects;

public interface ProjectsRepository extends JpaRepository<Projects, Long> {

}
