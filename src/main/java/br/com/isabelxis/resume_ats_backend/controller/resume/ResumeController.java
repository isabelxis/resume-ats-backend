package br.com.isabelxis.resume_ats_backend.controller.resume;

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

import br.com.isabelxis.resume_ats_backend.dto.resume.CreateResumeDTO;
import br.com.isabelxis.resume_ats_backend.dto.resume.FullResumeDTO;
import br.com.isabelxis.resume_ats_backend.dto.resume.ListResumeDTO;
import br.com.isabelxis.resume_ats_backend.dto.resume.UpdateResumeDTO;
import br.com.isabelxis.resume_ats_backend.service.resume.ResumeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/resumes")
@RequiredArgsConstructor
public class ResumeController {

    private final ResumeService resumeService;

    @PostMapping
    public ResponseEntity<ListResumeDTO> create( 
        @RequestBody
        CreateResumeDTO dto,
        Authentication auth
    ) {
        return ResponseEntity.ok(
                resumeService.create(auth.getName(), dto)
            );
    }

    @GetMapping
    public ResponseEntity<List<ListResumeDTO>> list(Authentication auth) {
        return ResponseEntity.ok(
                resumeService.list(auth.getName())
            );
    }

    @GetMapping("/{id}")
    public ResponseEntity<FullResumeDTO> get(
        @PathVariable
        Long id,

        Authentication auth
    ) {
        return ResponseEntity.ok(
                resumeService.getById(id, auth.getName())
            );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ListResumeDTO> update( 
        @PathVariable
        Long id,

        @RequestBody
        UpdateResumeDTO dto, 
        
        Authentication auth

    ) {
        return ResponseEntity.ok(
                resumeService.update(id, dto, auth.getName())
            );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
        @PathVariable
        Long id,

        Authentication auth
    ) {
        resumeService.delete(id, auth.getName());
        return ResponseEntity.noContent().build();
    }
    
}
