package br.com.isabelxis.resume_ats_backend.controller.auth;


import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.isabelxis.resume_ats_backend.dto.auth.AuthResponseDTO;
import br.com.isabelxis.resume_ats_backend.dto.auth.ForgotPasswordRequestDTO;
import br.com.isabelxis.resume_ats_backend.dto.auth.LoginRequestDTO;
import br.com.isabelxis.resume_ats_backend.dto.auth.RegisterRequestDTO;
import br.com.isabelxis.resume_ats_backend.dto.auth.ResetPasswordRequestDTO;
import br.com.isabelxis.resume_ats_backend.entity.user.User;
import br.com.isabelxis.resume_ats_backend.service.auth.AuthService;
import br.com.isabelxis.resume_ats_backend.service.jwt.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;


@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;
    
    @PostMapping("/register")
    public AuthResponseDTO register(@Valid @RequestBody RegisterRequestDTO request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDTO request, HttpServletResponse response) {
        
        AuthResponseDTO authResponse = authService.login(request);

        ResponseCookie refreshCookie = ResponseCookie
                    .from("refreshToken", authResponse.refreshToken())
                    .httpOnly(true)
                    .secure(false)
                    .path("/")
                    .maxAge(7 * 24 * 60 * 60)
                    .sameSite("Strict")
                    .build();

        response.addHeader("Set-Cookie", refreshCookie.toString());

        return ResponseEntity.ok(
            Map.of("accessToken",authResponse.accessToken(),
                    "user",authResponse.user()
                )   
        );
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

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(HttpServletRequest request) {

        String refreshToken = extractRefreshTokenFromCookie(request);

        if (refreshToken == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String email;
        
        try{
            email = jwtService.extractEmail(refreshToken);
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }


        User user = authService.findByEmail(email);

        if(!jwtService.isRefreshTokenValid(refreshToken, user)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String newAcceessToken = jwtService.generateAccessToken(user);

        return ResponseEntity.ok(
            Map.of("accessToken", newAcceessToken)
        );
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {

        ResponseCookie cookie = ResponseCookie
                .from("refreshToken", "")
                .httpOnly(true)
                .secure(false) // true em produção
                .path("/")
                .maxAge(0) // 🔥 isso remove o cookie
                .build();

        response.addHeader("Set-Cookie", cookie.toString());

        return ResponseEntity.ok().build();
    }
    //extrair cookie
    private String extractRefreshTokenFromCookie(HttpServletRequest request) {

        if (request.getCookies() == null) {
            return null;
        }

        for (var cookie : request.getCookies()) {
            if ("refreshToken".equals(cookie.getName())) {
                return cookie.getValue();
            }
        }

        return null;
    }


}  

