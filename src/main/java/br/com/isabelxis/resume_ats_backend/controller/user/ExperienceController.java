package br.com.isabelxis.resume_ats_backend.controller.user;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.isabelxis.resume_ats_backend.dto.user.experience.CreateExperienceDTO;
import br.com.isabelxis.resume_ats_backend.dto.user.experience.ListExperienceDTO;
import br.com.isabelxis.resume_ats_backend.dto.user.experience.UpdateExperienceDTO;
import br.com.isabelxis.resume_ats_backend.service.user.ExperienceService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/experiences")
@RequiredArgsConstructor
public class ExperienceController {

    private final ExperienceService experienceService;

    @PostMapping
    public ResponseEntity<ListExperienceDTO> create(

        @RequestBody
        CreateExperienceDTO dto,
        
        Authentication auth
    ) {
        return ResponseEntity.ok(
            experienceService.create(dto, auth.getName())
        );
    }
    
    @GetMapping
    public ResponseEntity<List<ListExperienceDTO>> list(
        Authentication auth
    ) {
        return ResponseEntity.ok(
            experienceService.list(auth.getName())
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ListExperienceDTO> update(
        @PathVariable
        Long id,

        @RequestBody
        UpdateExperienceDTO dto,
        
        Authentication auth
    ) {
        return ResponseEntity.ok(
            experienceService.update(id, dto, auth.getName()) 
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
        @PathVariable
        Long id,

        Authentication auth
    ) {
        experienceService.delete(id, auth.getName());
        return ResponseEntity.noContent().build();
    }
    
}
