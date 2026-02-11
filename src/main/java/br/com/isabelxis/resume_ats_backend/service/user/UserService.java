package br.com.isabelxis.resume_ats_backend.service.user;

import org.springframework.stereotype.Service;

import br.com.isabelxis.resume_ats_backend.entity.user.User;
import br.com.isabelxis.resume_ats_backend.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService{
    
    private final UserRepository userRepository;   
    
    public User updateProfile(String email, String name){
        
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        user.setName(name);
        return userRepository.save(user);
    }

    public User getProfile(String email){
        return userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }
}
