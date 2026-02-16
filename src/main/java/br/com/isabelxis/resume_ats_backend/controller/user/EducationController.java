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

import br.com.isabelxis.resume_ats_backend.dto.user.education.CreateEducationDTO;
import br.com.isabelxis.resume_ats_backend.dto.user.education.ListEducationDTO;
import br.com.isabelxis.resume_ats_backend.dto.user.education.UpdateEducationDTO;
import br.com.isabelxis.resume_ats_backend.service.user.EducationService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/educations")
@RequiredArgsConstructor
public class EducationController {

    private final EducationService educationService;

    @PostMapping
    public ResponseEntity<ListEducationDTO> create(

        @RequestBody
        CreateEducationDTO dto,

        Authentication auth
    ) {
        return ResponseEntity.ok(
            educationService.create(dto, auth.getName())
        );
    }

    @GetMapping
    public ResponseEntity<List<ListEducationDTO>> list(
        Authentication auth
    ) {
        return ResponseEntity.ok(
            educationService.list(auth.getName())
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ListEducationDTO> update(
        @PathVariable
        Long id,

        @RequestBody
        UpdateEducationDTO dto,
        
        Authentication auth
    ) {
        return ResponseEntity.ok(
            educationService.update(id, dto, auth.getName())
        );
    }  
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
        @PathVariable
        Long id,
        Authentication auth
    ) {
        educationService.delete(id, auth.getName());
        return ResponseEntity.noContent().build();
    }   
    
}
