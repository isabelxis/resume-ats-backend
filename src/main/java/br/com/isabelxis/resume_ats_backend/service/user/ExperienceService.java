package br.com.isabelxis.resume_ats_backend.service.user;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.isabelxis.resume_ats_backend.dto.user.experience.CreateExperienceDTO;
import br.com.isabelxis.resume_ats_backend.dto.user.experience.ListExperienceDTO;
import br.com.isabelxis.resume_ats_backend.dto.user.experience.UpdateExperienceDTO;
import br.com.isabelxis.resume_ats_backend.entity.user.Experience;
import br.com.isabelxis.resume_ats_backend.entity.user.User;
import br.com.isabelxis.resume_ats_backend.infra.exception.resume.ResourceNotFoundException;
import br.com.isabelxis.resume_ats_backend.repository.user.ExperienceRepository;
import br.com.isabelxis.resume_ats_backend.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExperienceService {

    private final ExperienceRepository experienceRepository;
    private final UserRepository userRepository;

    public ListExperienceDTO create(
        CreateExperienceDTO dto, 
        String email
    ) {

        User user = userRepository
            .findByEmail(email)
            .orElseThrow(() -> new ResourceNotFoundException("Usuario"));

        Experience experience = new Experience();
        experience.setUser(user);
        experience.setCompany(dto.company());
        experience.setPosition(dto.position());
        experience.setDescription(dto.description());
        experience.setStartDate(dto.startDate());
        experience.setEndDate(dto.endDate());
        experience.setSkills(dto.skills());
        experience.setModels(dto.models());
        experience.setCurrent(dto.current());
        experience.setLocation(dto.location());
        experience.setEmploymentType(dto.employmentType());

        Experience saved = experienceRepository.save(experience);

        return mapToDTO(saved);
    }

    public List<ListExperienceDTO> list(String email) {

        List<Experience> experiences = experienceRepository
            .findByUserEmail(email);

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
                e.getCurrent(),
                e.getLocation(),
                e.getEmploymentType()
            ))
            .toList();
    }

    public ListExperienceDTO update(
        Long id, 
        UpdateExperienceDTO dto, 
        String email
    ) {

        Experience experience = experienceRepository
            .findByIdAndUserEmail(id, email)
            .orElseThrow(() -> new ResourceNotFoundException("Experiencia"));

        if (dto.company() != null) experience.setCompany(dto.company());
        if (dto.position() != null) experience.setPosition(dto.position());
        if (dto.description() != null) experience.setDescription(dto.description());
        if (dto.startDate() != null) experience.setStartDate(dto.startDate());
        experience.setEndDate(dto.endDate());
        if (dto.skills() != null) experience.setSkills(dto.skills());
        if (dto.models() != null) experience.setModels(dto.models());
        experience.setCurrent(dto.current());
        if (dto.location() != null) experience.setLocation(dto.location());
        if (dto.employmentType() != null) experience.setEmploymentType(dto.employmentType());

        Experience updated = experienceRepository.save(experience);

        return mapToDTO(updated);
    }

    public void delete(Long id, String email) {

        Experience experience = experienceRepository
            .findByIdAndUserEmail(id, email)
            .orElseThrow(() -> new ResourceNotFoundException("Experiencia"));

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
            e.getCurrent(),
            e.getLocation(),
            e.getEmploymentType()
         );
    }
}
