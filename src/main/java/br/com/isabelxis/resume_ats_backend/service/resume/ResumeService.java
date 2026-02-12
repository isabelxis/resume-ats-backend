package br.com.isabelxis.resume_ats_backend.service.resume;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.isabelxis.resume_ats_backend.dto.resume.ListResumeDTO;
import br.com.isabelxis.resume_ats_backend.dto.resume.UpdateBasicDTO;
import br.com.isabelxis.resume_ats_backend.entity.resume.Resume;
import br.com.isabelxis.resume_ats_backend.entity.user.User;
import br.com.isabelxis.resume_ats_backend.repository.resume.ResumeRepository;
import br.com.isabelxis.resume_ats_backend.repository.user.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ResumeService {

    private ResumeRepository resumeRepository;
    private UserRepository userRepository;

    public ListResumeDTO create(String email) {
        
        User user = userRepository.findByEmail(email)
            .orElseThrow();

        Resume resume = new Resume();
        resume.setUser(user);
        
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
                r.getStatus(), 
                r.getCreatedAt()))
            .toList();
    }

    public ListResumeDTO update(Long id, UpdateBasicDTO dto, String email) {

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
            resume.getStatus(),
            resume.getCreatedAt()
        );
    }
}
