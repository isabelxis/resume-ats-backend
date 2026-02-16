package br.com.isabelxis.resume_ats_backend.service.resume;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.isabelxis.resume_ats_backend.dto.resume.CreateResumeDTO;
import br.com.isabelxis.resume_ats_backend.dto.resume.ListResumeDTO;
import br.com.isabelxis.resume_ats_backend.dto.resume.UpdateResumeDTO;
import br.com.isabelxis.resume_ats_backend.dto.user.experience.ListExperienceDTO;
import br.com.isabelxis.resume_ats_backend.entity.resume.Resume;
import br.com.isabelxis.resume_ats_backend.entity.user.Experience;
import br.com.isabelxis.resume_ats_backend.entity.user.User;
import br.com.isabelxis.resume_ats_backend.infra.exception.resume.ResourceNotFoundException;
import br.com.isabelxis.resume_ats_backend.repository.resume.ResumeRepository;
import br.com.isabelxis.resume_ats_backend.repository.user.ExperienceRepository;
import br.com.isabelxis.resume_ats_backend.repository.user.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ResumeService {

    private ResumeRepository resumeRepository;
    private UserRepository userRepository;
    private ExperienceRepository experienceRepository;

    public ListResumeDTO create(
        String email,
        CreateResumeDTO dto
        ) {
        
        User user = userRepository
            .findByEmail(email)
            .orElseThrow(() -> new ResourceNotFoundException("Usuario"));

        Resume resume = new Resume();
        resume.setUser(user);
        resume.setTitle(dto.title());
        resume.setSummary(dto.summary());
        
        Resume saved = resumeRepository.save(resume);
        return mapToDTO(saved);
    }

    public List<ListResumeDTO> list(String email) {
        List<Resume> resumes = 
            resumeRepository.findByUserEmailOrderByCreatedAtDesc(email);
        
        return resumes.stream()
            .map(r -> new ListResumeDTO(
                r.getId(), 
                r.getTitle(),
                r.getSummary(),
                r.getStatus(), 
                r.getCreatedAt()))
            .toList();
    }

    public ListResumeDTO update(Long id, UpdateResumeDTO dto, String email) {

        Resume resume = resumeRepository
            .findByIdAndUserEmail(id, email)
            .orElseThrow(() -> new ResourceNotFoundException("Curriculo"));


        resume.setTitle(dto.title());
        resume.setSummary(dto.summary());

        Resume updated = resumeRepository.save(resume);
        return mapToDTO(updated);
    }

    public void delete(Long id, String email) {
        Resume resume = resumeRepository
            .findByIdAndUserEmail(id, email)
            .orElseThrow(() -> new ResourceNotFoundException("Curriculo"));

        resumeRepository.delete(resume);
    }

    private ListResumeDTO mapToDTO(Resume resume) {
        return new ListResumeDTO(
            resume.getId(),
            resume.getTitle(),
            resume.getSummary(),
            resume.getStatus(),
            resume.getCreatedAt()
        );
    }

    private ListExperienceDTO mapExperienceToDTO(Experience e) {
        return new ListExperienceDTO(
            e.getId(),
            e.getCompany(),
            e.getPosition(),
            e.getDescription(),
            e.getStartDate(),
            e.getEndDate(),
            e.getSkills(),
            e.getModels(),
            e.getCurrent(),
            e.getLocation(),
            e.getEmploymentType()
        );
    }
}
