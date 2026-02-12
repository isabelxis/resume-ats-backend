package br.com.isabelxis.resume_ats_backend.controller.resume;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.isabelxis.resume_ats_backend.dto.resume.SkillGroupDTO;
import br.com.isabelxis.resume_ats_backend.service.resume.SkillsService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/resumes/{resumeId}/skills")
@RequiredArgsConstructor
public class SkillsController {

    private final SkillsService skillService;

    
    @PostMapping("/{id}/skills")
    public ResponseEntity<List<SkillGroupDTO>> getSkillsByResumeId(
        @PathVariable 
        Long id, 
        Authentication auth
    ) {
        return ResponseEntity.ok(
            skillService.getSkillsGroupedByType(id, auth.getName())
        );
    }
    
}
