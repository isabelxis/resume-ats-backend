package br.com.isabelxis.resume_ats_backend.controller.auth;


import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.isabelxis.resume_ats_backend.dto.user.AuthResponseDTO;
import br.com.isabelxis.resume_ats_backend.dto.user.ForgotPasswordRequestDTO;
import br.com.isabelxis.resume_ats_backend.dto.user.LoginRequestDTO;
import br.com.isabelxis.resume_ats_backend.dto.user.RegisterRequestDTO;
import br.com.isabelxis.resume_ats_backend.dto.user.ResetPasswordRequestDTO;
import br.com.isabelxis.resume_ats_backend.service.auth.AuthService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;


@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public AuthResponseDTO register(@Valid @RequestBody RegisterRequestDTO request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public AuthResponseDTO login(@Valid @RequestBody LoginRequestDTO request) {
        return authService.login(request);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(
            @Valid @RequestBody ForgotPasswordRequestDTO request) {
        authService.forgotPassword(request.email());
        return ResponseEntity.ok(
            Map.of("message", 
            "Se um usuário com esse email existir, um link de redefinição de senha será enviado.")
        );
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(
            @Valid @RequestBody ResetPasswordRequestDTO request) {
                
        authService.resetPassword(request);

        return ResponseEntity.ok(
            Map.of("message", "Senha redefinida com sucesso.")
        );
    }


}  

