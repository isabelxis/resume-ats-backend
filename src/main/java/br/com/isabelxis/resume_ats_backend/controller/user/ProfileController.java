package br.com.isabelxis.resume_ats_backend.controller.user;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.isabelxis.resume_ats_backend.dto.user.ProfileDTO;
import br.com.isabelxis.resume_ats_backend.dto.user.UpdateProfileRequestDTO;
import br.com.isabelxis.resume_ats_backend.service.user.UserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class ProfileController {
    
    private final UserService userService;

    @GetMapping
    public ResponseEntity<ProfileDTO> getCurrentUser(
            Authentication auth
        ) {
        return ResponseEntity.ok(
            userService.getProfile(auth.getName())
        );
    }

    @PutMapping("/me")
    public ResponseEntity<ProfileDTO> updateProfile(
        @RequestBody UpdateProfileRequestDTO dto,
        Authentication authentication){
        
        String email = authentication.getName();

        return ResponseEntity.ok(
            userService.updateProfile(email, dto, dto.name())
        );

        }
    

}
