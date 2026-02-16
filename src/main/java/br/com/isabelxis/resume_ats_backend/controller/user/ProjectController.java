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

import br.com.isabelxis.resume_ats_backend.dto.user.project.CreateProjectDTO;
import br.com.isabelxis.resume_ats_backend.dto.user.project.ListProjectDTO;
import br.com.isabelxis.resume_ats_backend.dto.user.project.UpdateProjectDTO;
import br.com.isabelxis.resume_ats_backend.service.user.ProjectsService;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/projects")
@AllArgsConstructor
public class ProjectController {

    public final ProjectsService projectsService;

    @PostMapping
    public ResponseEntity<ListProjectDTO> create(

        @RequestBody
        CreateProjectDTO dto,
    
         Authentication auth) {
        return ResponseEntity.ok(
            projectsService.create(dto, auth.getName())
        );
    }

    @GetMapping
    public ResponseEntity<List<ListProjectDTO>> list(
        Authentication auth
    ) {
        return ResponseEntity.ok(
            projectsService.list(auth.getName())
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ListProjectDTO> update(
        @PathVariable
        Long id,

        @RequestBody
        UpdateProjectDTO dto,

        Authentication auth
    ) {
        return ResponseEntity.ok(
            projectsService.update(id, dto, auth.getName())
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
        @PathVariable
        Long id,

        Authentication auth
    ) {
        projectsService.delete(id, auth.getName());
        return ResponseEntity.noContent().build();
    }
}
