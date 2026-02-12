package br.com.isabelxis.resume_ats_backend.service.resume;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.isabelxis.resume_ats_backend.dto.resume.SkillGroupDTO;
import br.com.isabelxis.resume_ats_backend.dto.resume.UpdateBasicDTO;
import br.com.isabelxis.resume_ats_backend.entity.resume.Experience;
import br.com.isabelxis.resume_ats_backend.entity.resume.Resume;
import br.com.isabelxis.resume_ats_backend.entity.resume.SkillType;
import br.com.isabelxis.resume_ats_backend.entity.resume.SkillTypeList;
import br.com.isabelxis.resume_ats_backend.entity.resume.Skills;
import br.com.isabelxis.resume_ats_backend.entity.user.User;
import br.com.isabelxis.resume_ats_backend.repository.resume.ResumeRepository;
import br.com.isabelxis.resume_ats_backend.repository.resume.SkillTypeRepository;
import br.com.isabelxis.resume_ats_backend.repository.resume.SkillsRepository;
import br.com.isabelxis.resume_ats_backend.repository.user.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ResumeService {

    private ResumeRepository resumeRepository;
    private UserRepository userRepository;
    private SkillsRepository skillRepository;
    private SkillTypeRepository skillTypeRepository;

    public Resume create(String email) {
        
        User user = userRepository.findByEmail(email)
            .orElseThrow();

        Resume resume = new Resume();
        resume.setUser(user);
        
        return resumeRepository.save(resume);
    }

    public Resume updateBasic(Long id, UpdateBasicDTO dto, String email) {

        Resume resume = resumeRepository
            .findByIdAndUserEmail(id, email)
            .orElseThrow(() -> new RuntimeException("Acesso Negado ou Curriculo não encontrado"));


        resume.setTitle(dto.title());
        resume.setSummary(dto.summary());

        return resumeRepository.save(resume);
    }

    //incluir experiencias
    public Resume updateExperiences(Long id, List<String> experiences, String email) {

        Resume resume = resumeRepository
            .findByIdAndUserEmail(id, email)
            .orElseThrow(() -> new RuntimeException("Acesso Negado ou Curriculo não encontrado"));

        List<Experience> experienceList = experiences.stream()
            .map(exp -> {
                Experience experience = new Experience();
                experience.setDescription(exp);
                return experience;
            })
            .collect(Collectors.toList());
        
        resume.setExperiences(experienceList);

        return resumeRepository.save(resume);
    }

    public Skills addSkillToResume(
        Long resumeId, 
        String skillName, 
        SkillTypeList type, 
        String email
    ){

        Resume resume = resumeRepository
            .findByIdAndUserEmail(resumeId, email)
            .orElseThrow(() -> new RuntimeException("Acesso Negado ou Curriculo não encontrado"));

        SkillType skillType = skillTypeRepository
            .findByNameIgnoreCase(skillName)
            .orElseGet(() -> {
                SkillType newSkill = new SkillType();
                newSkill.setName(skillName);
                newSkill.setType(type);
                return skillTypeRepository.save(newSkill);
            });

        Skills skills = new Skills();
        skills.setResume(resume);
        skills.setSkillType(skillType);

        return skillRepository.save(skills);
    }

    public List<SkillGroupDTO> getSkillsGroupedByType(Long resumeId, String email) {

        Resume resume = resumeRepository
            .findByIdAndUserEmail(resumeId, email)
            .orElseThrow(() -> new RuntimeException("Acesso Negado ou Curriculo não encontrado"));

        List<Skills> skills = skillRepository.findByResumeId(resumeId)
            .orElseThrow(() -> new RuntimeException("Nenhuma habilidade encontrada para este currículo"));

        Map<SkillTypeList, List<String>> grouped = 
            skills.stream()
                .collect(Collectors.groupingBy(
                    s -> s.getSkillType().getType(), 
                    Collectors.mapping(s -> s.getSkillType().getName(), Collectors.toList())
            ));
        return grouped.entrySet()
            .stream()
            .map(entry -> new SkillGroupDTO(
                formatCategory(entry.getKey()),
                entry.getValue()
            ))
            .toList();
    }

    private String formatCategory(SkillTypeList type) {
        return switch (type) {
            case LANGUAGE -> "Languages";
            case FRAMEWORK -> "Frameworks";
            case DATABASE -> "Databases";
            case PROGRAMMING -> "Programming Languages";
            case API -> "APIs";
            case AGILE -> "Agile Methodologies";
            case TOOL -> "Tools";
        };
    }
}
