package br.com.isabelxis.resume_ats_backend.controller.user;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.isabelxis.resume_ats_backend.dto.user.skill.CreateSkillDTO;
import br.com.isabelxis.resume_ats_backend.dto.user.skill.ListSkillDTO;
import br.com.isabelxis.resume_ats_backend.service.user.SkillsService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/skills")
@RequiredArgsConstructor
public class SkillController {

    private final SkillsService skillService;

    @PostMapping
    public ResponseEntity<ListSkillDTO> create(
        @RequestBody
        CreateSkillDTO dto,

        Authentication auth
    ) {
        return ResponseEntity.ok(
            skillService.create(dto, auth.getName())
        );
    }

    @GetMapping
    public ResponseEntity<List<ListSkillDTO>> list(
        Authentication auth
    ) {
        return ResponseEntity.ok(
            skillService.list(auth.getName())
        );
    }
    
}
