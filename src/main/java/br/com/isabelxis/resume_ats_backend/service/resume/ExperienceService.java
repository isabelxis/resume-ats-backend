package br.com.isabelxis.resume_ats_backend.service.resume;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.isabelxis.resume_ats_backend.dto.resume.CreateExperienceDTO;
import br.com.isabelxis.resume_ats_backend.dto.resume.ListExperienceDTO;
import br.com.isabelxis.resume_ats_backend.dto.resume.UpdateExperienceDTO;
import br.com.isabelxis.resume_ats_backend.entity.resume.Experience;
import br.com.isabelxis.resume_ats_backend.entity.resume.Resume;
import br.com.isabelxis.resume_ats_backend.repository.resume.ExperienceRepository;
import br.com.isabelxis.resume_ats_backend.repository.resume.ResumeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExperienceService {

    private final ExperienceRepository experienceRepository;
    private final ResumeRepository resumeRepository;


    public ListExperienceDTO create(
        Long resumeId, 
        CreateExperienceDTO dto, 
        String email
    ) {

        Resume resume = resumeRepository
            .findByIdAndUserEmail(resumeId, email)
            .orElseThrow(() -> new RuntimeException("Acesso Negado ou Curriculo não encontrado"));

        Experience experience = new Experience();
        experience.setCompany(dto.company());
        experience.setPosition(dto.position());
        experience.setDescription(dto.description());
        experience.setStartDate(dto.startDate());
        experience.setEndDate(dto.endDate());
        experience.setSkills(dto.skills());
        experience.setModels(dto.models());
        experience.setCurrent(dto.current());
        experience.setResume(resume);

        Experience saved = experienceRepository.save(experience);

        return mapToDTO(saved);
    }

    public List<ListExperienceDTO> list(Long resumeId, String email) {

        Resume resume = resumeRepository
            .findByIdAndUserEmail(resumeId, email)
            .orElseThrow(() -> new RuntimeException("Acesso Negado ou Curriculo não encontrado"));

        List<Experience> experiences = experienceRepository
            .findByResumeId(resume.getId());

        return experiences.stream()
            .map(e -> new ListExperienceDTO(
                e.getId(),
                e.getCompany(),
                e.getPosition(),
                e.getDescription(),
                e.getStartDate(),
                e.getEndDate(),
                e.getSkills(),
                e.getModels(),
                e.getCurrent()
            ))
            .toList();
    }

    public ListExperienceDTO update(
        Long experienceId, 
        UpdateExperienceDTO dto, 
        String email
    ) {

        Experience experience = experienceRepository
            .findByIdAndResumeUserEmail(experienceId, email)
            .orElseThrow(() -> new RuntimeException("Acesso Negado ou Experiencia não encontrada"));

        experience.setCompany(dto.company());
        experience.setPosition(dto.position());
        experience.setDescription(dto.description());
        experience.setStartDate(dto.startDate());
        experience.setEndDate(dto.endDate());
        experience.setSkills(dto.skills());
        experience.setModels(dto.models());
        experience.setCurrent(dto.current());

        Experience updated = experienceRepository.save(experience);

        return mapToDTO(updated);
    }

    public void delete(Long experienceId, String email) {

        Experience experience = experienceRepository
            .findByIdAndResumeUserEmail(experienceId, email)
            .orElseThrow(() -> new RuntimeException("Acesso Negado ou Experiencia não encontrada"));

        experienceRepository.delete(experience);
    }
    
    private ListExperienceDTO mapToDTO(Experience e) {
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
