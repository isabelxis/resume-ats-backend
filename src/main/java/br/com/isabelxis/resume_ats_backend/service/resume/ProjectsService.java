package br.com.isabelxis.resume_ats_backend.service.resume;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.isabelxis.resume_ats_backend.dto.resume.project.CreateProjectDTO;
import br.com.isabelxis.resume_ats_backend.dto.resume.project.ListProjectDTO;
import br.com.isabelxis.resume_ats_backend.dto.resume.project.UpdateProjectDTO;
import br.com.isabelxis.resume_ats_backend.entity.resume.Project;
import br.com.isabelxis.resume_ats_backend.entity.resume.Resume;
import br.com.isabelxis.resume_ats_backend.infra.exception.resume.ResourceNotFoundException;
import br.com.isabelxis.resume_ats_backend.repository.resume.ProjectsRepository;
import br.com.isabelxis.resume_ats_backend.repository.resume.ResumeRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectsService {

    public final ProjectsRepository projectsRepository;
    public final ResumeRepository resumeRepository;

    public ListProjectDTO create(
        Long resumeId, 
        CreateProjectDTO dto, 
        String email) {
        
        Resume resume = resumeRepository
            .findByIdAndUserEmail(resumeId, email)
            .orElseThrow(() -> new ResourceNotFoundException("Curriculo"));
        
        Project project = new Project();
        project.setName(dto.name());
        project.setDescription(dto.description());
        project.setLink(dto.link());
        project.setTechnologies(dto.technologies());
        project.setResume(resume);

        Project saved = projectsRepository.save(project);
        return mapToDTO(saved);
    }

    public List<ListProjectDTO> list(Long resumeId, String email) {

        Resume resume = resumeRepository
            .findByIdAndUserEmail(resumeId, email)
            .orElseThrow(() -> new ResourceNotFoundException("Curriculo"));
        
        List<Project> projects = projectsRepository
            .findByResumeId(resume.getId());

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
        Long projectId, 
        UpdateProjectDTO dto, 
        String email) {

        Project project = projectsRepository
            .findByIdAndResumeUserEmail(projectId, email)
            .orElseThrow(() -> new ResourceNotFoundException("Projeto"));
        
        project.setName(dto.name());
        project.setDescription(dto.description());
        project.setLink(dto.link());
        project.setTechnologies(dto.technologies());

        Project updated = projectsRepository.save(project);
        return mapToDTO(updated);
    }

    public void delete(Long projectId, String email) {

        Project project = projectsRepository
            .findByIdAndResumeUserEmail(projectId, email)
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
