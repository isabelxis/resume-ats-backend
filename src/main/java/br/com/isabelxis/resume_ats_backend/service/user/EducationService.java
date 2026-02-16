package br.com.isabelxis.resume_ats_backend.service.user;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.isabelxis.resume_ats_backend.dto.user.education.CreateEducationDTO;
import br.com.isabelxis.resume_ats_backend.dto.user.education.ListEducationDTO;
import br.com.isabelxis.resume_ats_backend.dto.user.education.UpdateEducationDTO;
import br.com.isabelxis.resume_ats_backend.entity.user.Education;
import br.com.isabelxis.resume_ats_backend.entity.user.User;
import br.com.isabelxis.resume_ats_backend.infra.exception.resume.ResourceNotFoundException;
import br.com.isabelxis.resume_ats_backend.repository.user.EducationRepository;
import br.com.isabelxis.resume_ats_backend.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EducationService {

    private final EducationRepository educationRepository;
    private final UserRepository userRepository;

    public ListEducationDTO create(
            CreateEducationDTO dto,
            String email) {

            User user = userRepository
            .findByEmail(email)
            .orElseThrow(() -> new ResourceNotFoundException("Usuario"));

                 
            Education education = new Education();
            education.setUser(user);
            education.setInstitution(dto.institution());    
            education.setDegree(dto.degree());
            education.setStartDate(dto.startDate());
            education.setEndDate(dto.endDate());
            education.setGPA(dto.GPA());
            education.setSkills(dto.skills());

            Education saved = educationRepository.save(education);
            return mapToDTO(saved);
        }
    
    public List<ListEducationDTO> list(String email) {
 
        List<Education> educations = educationRepository
            .findByUserEmail(email);

        return educations.stream()
            .map(edu -> new ListEducationDTO(
                edu.getId(),
                edu.getInstitution(),
                edu.getDegree(),
                edu.getStartDate(),
                edu.getEndDate(),
                edu.getGPA(),
                edu.getSkills()
            ))
            .toList();
    }

    public ListEducationDTO update(
        Long id, 
        UpdateEducationDTO dto, 
        String email
    ) {
        Education education = educationRepository
            .findByIdAndUserEmail(id, email)
            .orElseThrow(() -> new ResourceNotFoundException("Educação"));

        if(dto.institution() !=null) education.setInstitution(dto.institution());  
        if(dto.degree() != null) education.setDegree(dto.degree());
        if(dto.startDate() != null) education.setStartDate(dto.startDate());
        education.setEndDate(dto.endDate());
        education.setGPA(dto.GPA());
        education.setSkills(dto.skills());

        Education saved = educationRepository.save(education);
        return mapToDTO(saved);
    }

    public void delete(Long id, String email) {
        Education education = educationRepository
            .findByIdAndUserEmail(id, email)
            .orElseThrow(() -> new ResourceNotFoundException("Educação"));

        educationRepository.delete(education);
    }
    
    private ListEducationDTO mapToDTO(Education education) {
        return new ListEducationDTO(
            education.getId(),
            education.getInstitution(),
            education.getDegree(),
            education.getStartDate(),
            education.getEndDate(),
            education.getGPA(),
            education.getSkills()
        );
    }
}
