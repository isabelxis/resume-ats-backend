package br.com.isabelxis.resume_ats_backend.service.resume;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.isabelxis.resume_ats_backend.dto.resume.CreateEducationDTO;
import br.com.isabelxis.resume_ats_backend.dto.resume.ListEducationDTO;
import br.com.isabelxis.resume_ats_backend.dto.resume.UpdateEducationDTO;
import br.com.isabelxis.resume_ats_backend.entity.resume.Education;
import br.com.isabelxis.resume_ats_backend.entity.resume.Resume;
import br.com.isabelxis.resume_ats_backend.infra.exception.resume.ResourceNotFoundException;
import br.com.isabelxis.resume_ats_backend.repository.resume.EducationRepository;
import br.com.isabelxis.resume_ats_backend.repository.resume.ResumeRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EducationService {

    private final EducationRepository educationRepository;
    private final ResumeRepository resumeRepository;

    public ListEducationDTO create(
            Long resumeId, 
            CreateEducationDTO dto,
            String email) {
            
            Resume resume = resumeRepository
                .findByIdAndUserEmail(resumeId, email)
                .orElseThrow(() -> new ResourceNotFoundException("Curriculo"));
                
            Education education = new Education();
            education.setInstitution(dto.institution());    
            education.setDegree(dto.degree());
            education.setStartDate(dto.startDate());
            education.setEndDate(dto.endDate());
            education.setGPA(dto.GPA());
            education.setSkills(dto.skills());
            education.setResume(resume);

            Education saved = educationRepository.save(education);
            return mapToDTO(saved);
        }
    
    public List<ListEducationDTO> list(Long resumeId, String email) {
        Resume resume = resumeRepository
            .findByIdAndUserEmail(resumeId, email)
            .orElseThrow(() -> new ResourceNotFoundException("Curriculo"));

        List<Education> educations = educationRepository
            .findByResumeId(resume.getId());

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
            .findByIdAndResumeUserEmail(id, email)
            .orElseThrow(() -> new ResourceNotFoundException("Educação"));

        education.setInstitution(dto.institution());    
        education.setDegree(dto.degree());
        education.setStartDate(dto.startDate());
        education.setEndDate(dto.endDate());
        education.setGPA(dto.GPA());
        education.setSkills(dto.skills());

        Education saved = educationRepository.save(education);
        return mapToDTO(saved);
    }

    public void delete(Long id, String email) {
        Education education = educationRepository
            .findByIdAndResumeUserEmail(id, email)
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
