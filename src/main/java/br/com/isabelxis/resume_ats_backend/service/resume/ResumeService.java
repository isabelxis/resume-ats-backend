package br.com.isabelxis.resume_ats_backend.service.resume;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.isabelxis.resume_ats_backend.dto.resume.CreateResumeDTO;
import br.com.isabelxis.resume_ats_backend.dto.resume.FullResumeDTO;
import br.com.isabelxis.resume_ats_backend.dto.resume.ListExperienceDTO;
import br.com.isabelxis.resume_ats_backend.dto.resume.ListResumeDTO;
import br.com.isabelxis.resume_ats_backend.dto.resume.UpdateResumeDTO;
import br.com.isabelxis.resume_ats_backend.dto.user.ProfileDTO;
import br.com.isabelxis.resume_ats_backend.entity.resume.Experience;
import br.com.isabelxis.resume_ats_backend.entity.resume.Resume;
import br.com.isabelxis.resume_ats_backend.entity.user.User;
import br.com.isabelxis.resume_ats_backend.repository.resume.ExperienceRepository;
import br.com.isabelxis.resume_ats_backend.repository.resume.ResumeRepository;
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
        
        User user = userRepository.findByEmail(email)
            .orElseThrow();

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

    public FullResumeDTO getById(Long id, String email) {
        Resume resume = resumeRepository
            .findByIdAndUserEmail(id, email)
            .orElseThrow(() -> new RuntimeException("Curriculo não encontrado ou acesso negado"));

        ProfileDTO profileDTO = new ProfileDTO(
            resume.getUser().getId(),
            resume.getUser().getName(),
            resume.getUser().getEmail(),
            resume.getUser().getPhone(),
            resume.getUser().getLinkedin(),
            resume.getUser().getGithub(),
            resume.getUser().getPortfolio()
        );

        List<ListExperienceDTO> experiences = 
            experienceRepository
                .findByResumeIdAndResumeUserEmailOrderByDisplayOrderDesc(id, email)
                .stream()
                .map(this::mapExperienceToDTO)
                .toList();                                        

        return new FullResumeDTO(
            resume.getId(),
            resume.getTitle(),
            resume.getSummary(),
            profileDTO,
            experiences
        );
    }

    public ListResumeDTO update(Long id, UpdateResumeDTO dto, String email) {

        Resume resume = resumeRepository
            .findByIdAndUserEmail(id, email)
            .orElseThrow(() -> new RuntimeException("Curriculo não encontrado ou acesso negado"));


        resume.setTitle(dto.title());
        resume.setSummary(dto.summary());

        Resume updated = resumeRepository.save(resume);
        return mapToDTO(updated);
    }

    public void delete(Long id, String email) {
        Resume resume = resumeRepository
            .findByIdAndUserEmail(id, email)
            .orElseThrow(() -> new RuntimeException("Curriculo não encontrado ou acesso negado"));

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
            e.getCurrent()
        );
    }
}
