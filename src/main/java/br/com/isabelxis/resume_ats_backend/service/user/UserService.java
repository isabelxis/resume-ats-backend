package br.com.isabelxis.resume_ats_backend.service.user;

import org.springframework.stereotype.Service;

import br.com.isabelxis.resume_ats_backend.dto.user.ProfileDTO;
import br.com.isabelxis.resume_ats_backend.dto.user.UpdateProfileDTO;
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
        UpdateProfileDTO dto
    ){
        
        User user = userRepository
            .findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Usuário"));

        if (dto.name() != null) user.setName(dto.name());
        if (dto.phone() != null) user.setPhone(dto.phone());
        if (dto.location() != null) user.setLocation(dto.location());
        if (dto.linkedin() != null) user.setLinkedin(dto.linkedin()); 
        if (dto.github() != null) user.setGithub(dto.github());
        if (dto.portfolio() != null) user.setPortfolio(dto.portfolio());
        if (dto.headline() != null) user.setHeadline(dto.headline());
        
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
            user.getPlan().name(),
            user.getPhone(),
            user.getLocation(),
            user.getLinkedin(),
            user.getGithub(),
            user.getPortfolio(),
            user.getHeadline()
        );
    }
}
