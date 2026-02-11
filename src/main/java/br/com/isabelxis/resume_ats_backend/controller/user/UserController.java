package br.com.isabelxis.resume_ats_backend.controller.user;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.isabelxis.resume_ats_backend.dto.user.UpdateProfileRequestDTO;
import br.com.isabelxis.resume_ats_backend.entity.user.User;
import br.com.isabelxis.resume_ats_backend.service.user.UserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    
    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<User> getCurrentUser(Authentication authentication) {
        
        String email = authentication.getName();
        return ResponseEntity.ok(
            userService.getProfile(email)
        );
    }

    @PutMapping("/me")
    public ResponseEntity<User> updateProfile(
        @RequestBody UpdateProfileRequestDTO request,
        Authentication authentication){
        
        String email = authentication.getName();

        return ResponseEntity.ok(
            userService.updateProfile(email, request.name())
        );

        }
    

}
