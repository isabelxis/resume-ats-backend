package br.com.isabelxis.resume_ats_backend.controller.resume;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.isabelxis.resume_ats_backend.dto.resume.SkillGroupDTO;
import br.com.isabelxis.resume_ats_backend.dto.resume.UpdateBasicDTO;
import br.com.isabelxis.resume_ats_backend.entity.resume.Resume;
import br.com.isabelxis.resume_ats_backend.service.resume.ResumeService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/resumes")
@RequiredArgsConstructor
public class ResumeController {

    private final ResumeService resumeService;

    @PostMapping
    public ResponseEntity<Resume> create( Authentication auth) {
        return ResponseEntity.ok(
                resumeService.create(auth.getName())
            );
    }

    @PostMapping("/{id}/basic")
    public ResponseEntity<Resume> updateBasic( 
        @PathVariable
        Long id,

        @RequestBody
        UpdateBasicDTO dto, 
        
        Authentication auth

    ) {
        return ResponseEntity.ok(
                resumeService.updateBasic(id, dto, auth.getName())
            );
    }

    @PostMapping("/{id}/experiences")
    public ResponseEntity<Resume> updateExperiences( 
        @PathVariable
        Long id,

        @RequestBody
        List<String> experiences, 
        
        Authentication auth

    ) {
        return ResponseEntity.ok(
                resumeService.updateExperiences(id, experiences, auth.getName())
            );
    }

    @PostMapping("/{id}/skills")
    public ResponseEntity<List<SkillGroupDTO>> getSkillsByResumeId(
        @PathVariable 
        Long id, 
        Authentication auth
    ) {
        return ResponseEntity.ok(
            resumeService.getSkillsGroupedByType(id, auth.getName())
        );
    }
    
}
