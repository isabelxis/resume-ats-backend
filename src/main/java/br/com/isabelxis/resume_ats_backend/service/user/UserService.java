package br.com.isabelxis.resume_ats_backend.service.user;

import org.springframework.stereotype.Service;
import org.springframework.web.method.annotation.RequestParamMethodArgumentResolver;

import br.com.isabelxis.resume_ats_backend.dto.user.ProfileDTO;
import br.com.isabelxis.resume_ats_backend.dto.user.UpdateProfileRequestDTO;
import br.com.isabelxis.resume_ats_backend.entity.user.User;
import br.com.isabelxis.resume_ats_backend.infra.exception.resume.ResourceNotFoundException;
import br.com.isabelxis.resume_ats_backend.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService{
    
    private final UserRepository userRepository;   
    
    public ProfileDTO updateProfile(
        String email,
        UpdateProfileRequestDTO dto,
        String name
    ){
        
        User user = userRepository
            .findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        user.setName(dto.name());
        user.setEmail(dto.email());
        user.setPhone(dto.phone());
        user.setLinkedin(dto.linkedin()); 
        user.setGithub(dto.github());
        user.setPortfolio(dto.portfolio());
        
        User updatedUser = userRepository.save(user);

        return mapToDTO(updatedUser);
    }

    public ProfileDTO getProfile(String email){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário"));
        return mapToDTO(user);
    }

    private ProfileDTO mapToDTO(User user) {
        return new ProfileDTO(
            user.getId(),
            user.getName(),
            user.getEmail(),
            user.getPhone(),
            user.getLinkedin(),
            user.getGithub(),
            user.getPortfolio()
        );
    }
}
