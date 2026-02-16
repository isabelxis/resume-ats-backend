package br.com.isabelxis.resume_ats_backend.service.user;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.isabelxis.resume_ats_backend.dto.user.project.CreateProjectDTO;
import br.com.isabelxis.resume_ats_backend.dto.user.project.ListProjectDTO;
import br.com.isabelxis.resume_ats_backend.dto.user.project.UpdateProjectDTO;
import br.com.isabelxis.resume_ats_backend.entity.user.Project;
import br.com.isabelxis.resume_ats_backend.entity.user.User;
import br.com.isabelxis.resume_ats_backend.infra.exception.resume.ResourceNotFoundException;
import br.com.isabelxis.resume_ats_backend.repository.user.ProjectsRepository;
import br.com.isabelxis.resume_ats_backend.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectsService {

    public final ProjectsRepository projectsRepository;
    public final UserRepository userRepository;

    public ListProjectDTO create(
        CreateProjectDTO dto, 
        String email) {

        User user = userRepository
            .findByEmail(email)
            .orElseThrow(() -> new ResourceNotFoundException("Usuario"));
        
        Project project = new Project();
        project.setUser(user);
        project.setName(dto.name());
        project.setDescription(dto.description());
        project.setLink(dto.link());
        project.setTechnologies(dto.technologies());

        Project saved = projectsRepository.save(project);
        return mapToDTO(saved);
    }

    public List<ListProjectDTO> list(String email) {

        List<Project> projects = projectsRepository
            .findByUserEmail(email);

        return projects.stream()
            .map(p -> new ListProjectDTO(
                p.getId(),
                p.getName(),
                p.getDescription(),
                p.getLink(),
                p.getTechnologies()
            ))
            .toList();
    }

    public ListProjectDTO update(
        Long id, 
        UpdateProjectDTO dto, 
        String email) {

        Project project = projectsRepository
            .findByIdAndUserEmail(id, email)
            .orElseThrow(() -> new ResourceNotFoundException("Projeto"));
        
        if(dto.name() != null) project.setName(dto.name());
        if(dto.description() != null) project.setDescription(dto.description());
        project.setLink(dto.link());
        project.setTechnologies(dto.technologies());

        Project updated = projectsRepository.save(project);
        return mapToDTO(updated);
    }

    public void delete(Long id, String email) {

        Project project = projectsRepository
            .findByIdAndUserEmail(id, email)
            .orElseThrow(() -> new ResourceNotFoundException("Projeto"));
        
        projectsRepository.delete(project);
    }

    private ListProjectDTO mapToDTO(Project project) {
        return new ListProjectDTO(
            project.getId(),
            project.getName(),
            project.getDescription(),
            project.getLink(),
            project.getTechnologies()
        );
    }
    
}
