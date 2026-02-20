package br.com.isabelxis.resume_ats_backend.service.auth;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.isabelxis.resume_ats_backend.dto.auth.AuthResponseDTO;
import br.com.isabelxis.resume_ats_backend.dto.auth.LoginRequestDTO;
import br.com.isabelxis.resume_ats_backend.dto.auth.RegisterRequestDTO;
import br.com.isabelxis.resume_ats_backend.dto.auth.ResetPasswordRequestDTO;
import br.com.isabelxis.resume_ats_backend.dto.user.UserResponseDTO;
import br.com.isabelxis.resume_ats_backend.entity.auth.PasswordResetToken;
import br.com.isabelxis.resume_ats_backend.entity.auth.Plan;
import br.com.isabelxis.resume_ats_backend.entity.user.User;
import br.com.isabelxis.resume_ats_backend.infra.exception.auth.EmailAlreadyExistsException;
import br.com.isabelxis.resume_ats_backend.infra.exception.auth.InvalidPasswordException;
import br.com.isabelxis.resume_ats_backend.infra.exception.auth.InvalidResetTokenException;
import br.com.isabelxis.resume_ats_backend.infra.exception.auth.UserNotFoundException;
import br.com.isabelxis.resume_ats_backend.repository.auth.PasswordResetTokenRepository;
import br.com.isabelxis.resume_ats_backend.repository.user.UserRepository;
import br.com.isabelxis.resume_ats_backend.service.jwt.JwtService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthService {
    
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final PasswordResetTokenRepository tokenRepository;
    private final EmailService emailService;

    public AuthResponseDTO register(RegisterRequestDTO request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new EmailAlreadyExistsException(request.email());
        }

        User user = new User();
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setPlan(Plan.FREE);
        userRepository.save(user);

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        return new AuthResponseDTO(
            accessToken,
            refreshToken,
            new UserResponseDTO(user.getId(), user.getEmail(), user.getPlan().name()));
    }

    public AuthResponseDTO login(LoginRequestDTO request) {

        User user = userRepository.findByEmail(request.email())
                .orElseThrow(UserNotFoundException::new);

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new InvalidPasswordException();
        }

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);


        return new AuthResponseDTO(
            accessToken,
            refreshToken,
            new UserResponseDTO(
                user.getId(),
                user.getEmail(),
                user.getPlan().name()
            )
        );
    }

    public void forgotPassword(String email) {
        userRepository.findByEmail(email).ifPresent(user ->{

            String token = UUID.randomUUID().toString();

            PasswordResetToken resetToken = new PasswordResetToken();
                resetToken.setToken(token);
                resetToken.setUser(user);
                resetToken.setExpiresAt(LocalDateTime.now().plusMinutes(15));
                resetToken.setUsed(false);               
            
                tokenRepository.save(resetToken);

                emailService.sendPasswordResetEmail(user.getEmail(), token);
        }
        );
    }

    public void resetPassword(ResetPasswordRequestDTO request) {

        PasswordResetToken resetToken = tokenRepository
                .findByTokenAndUsedFalse(request.token())
                .orElseThrow(() -> new InvalidResetTokenException("Token inválido ou já utilizado"));

        if (resetToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new InvalidResetTokenException("Token expirado ou já utilizado");
        }

        User user = resetToken.getUser();
        user.setPassword(passwordEncoder.encode(request.newPassword()));
        userRepository.save(user);

        resetToken.setUsed(true);
        tokenRepository.save(resetToken);
    }

    public User findByEmail(String email){
        return userRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("Usuário")
            );
    }
}