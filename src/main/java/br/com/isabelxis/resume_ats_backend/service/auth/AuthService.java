package br.com.isabelxis.resume_ats_backend.service.auth;

import org.springframework.boot.webmvc.autoconfigure.WebMvcProperties.Apiversion.Use;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.isabelxis.resume_ats_backend.dto.user.AuthResponseDTO;
import br.com.isabelxis.resume_ats_backend.dto.user.LoginRequestDTO;
import br.com.isabelxis.resume_ats_backend.dto.user.RegisterRequestDTO;
import br.com.isabelxis.resume_ats_backend.dto.user.UserResponseDTO;
import br.com.isabelxis.resume_ats_backend.entity.user.Plan;
import br.com.isabelxis.resume_ats_backend.entity.user.User;
import br.com.isabelxis.resume_ats_backend.infra.exception.EmailAlreadyExistsException;
import br.com.isabelxis.resume_ats_backend.infra.exception.InvalidPasswordException;
import br.com.isabelxis.resume_ats_backend.infra.exception.UserNotFoundException;
import br.com.isabelxis.resume_ats_backend.repository.user.UserRepository;
import br.com.isabelxis.resume_ats_backend.service.jwt.JwtService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthService {
    
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthResponseDTO register(RegisterRequestDTO request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new EmailAlreadyExistsException(request.email());
        }

        User user = new User();
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setPlan(Plan.FREE);
        userRepository.save(user);

        String token = jwtService.generateToken(user);
        return new AuthResponseDTO(
            token,
            new UserResponseDTO(user.getId(), user.getEmail(), user.getPlan().name()));
    }

    public AuthResponseDTO login(LoginRequestDTO request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(UserNotFoundException::new);

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new InvalidPasswordException();
        }

        String token = jwtService.generateToken(user);

        return new AuthResponseDTO(
            token,
            new UserResponseDTO(
                user.getId(), 
                user.getEmail(),
                user.getPlan().name()
            )
        );
    }

}