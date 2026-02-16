package br.com.isabelxis.resume_ats_backend.service.user;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.isabelxis.resume_ats_backend.dto.user.skill.CreateSkillDTO;
import br.com.isabelxis.resume_ats_backend.dto.user.skill.ListSkillDTO;
import br.com.isabelxis.resume_ats_backend.entity.user.Skill;
import br.com.isabelxis.resume_ats_backend.entity.user.SkillCategory;
import br.com.isabelxis.resume_ats_backend.entity.user.User;
import br.com.isabelxis.resume_ats_backend.infra.exception.resume.ResourceNotFoundException;
import br.com.isabelxis.resume_ats_backend.repository.user.SkillRepository;
import br.com.isabelxis.resume_ats_backend.repository.user.UserRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SkillsService {

    private SkillRepository skillRepository;
    private UserRepository userRepository;

    public ListSkillDTO create(
        CreateSkillDTO dto,
        String email
    ) {
        User user = userRepository
            .findByEmail(email)
            .orElseThrow(() -> new ResourceNotFoundException("Usuario"));

        Skill skill = new Skill();
        skill.setUser(user);
        skill.setName(dto.name());
        skill.setCategory(dto.category());

        Skill saved = skillRepository.save(skill);
        return mapToDTO(saved);
    }

    public List<ListSkillDTO> list(String email) {

        List<Skill> skills = skillRepository
            .findByUserEmail(email);

        return skills.stream()
            .map(s -> new ListSkillDTO(
                s.getId(),
                s.getName(),
                formatCategory(s.getCategory())
            ))
            .toList();
    }

    private ListSkillDTO mapToDTO(Skill skill) {
        return new ListSkillDTO(
            skill.getId(),
            skill.getName(),
            formatCategory(skill.getCategory())
        );
    }

    private String formatCategory(SkillCategory type) {
        return switch (type) {
            case LANGUAGES -> "Linguagens";
            case FRAMEWORKS_LIBRARIES -> "Frameworks/Bibliotecas";
            case DATABASES -> "Bancos de Dados";
            case PROGRAMMING -> "Linguagens de Programação";
            case APIS -> "APIs";
            case AGILE_METHODOLOGIES -> "Metodologias Ágeis";
            case TOOLS -> "Ferramentas";
            case HARD_SKILLS -> "Habilidades Técnicas";
            case SOFT_SKILLS -> "Habilidades Interpessoais";    

        };
    }
}
