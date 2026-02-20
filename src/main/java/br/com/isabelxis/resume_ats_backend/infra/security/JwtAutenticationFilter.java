package br.com.isabelxis.resume_ats_backend.infra.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.isabelxis.resume_ats_backend.entity.user.User;
import br.com.isabelxis.resume_ats_backend.repository.user.UserRepository;
import br.com.isabelxis.resume_ats_backend.service.jwt.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class JwtAutenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, 
            HttpServletResponse response, 
            FilterChain filterChain
        )throws ServletException, IOException {

            final String authHeader = request.getHeader("Authorization");
            
            // Verifica se o header de autorização está presente e começa com "Bearer " (que é o formato comum para tokens JWT)
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
                return;
            }

            final String token = authHeader.substring(7); // Remove "Bearer
                        String email;

            try {
                email = jwtService.extractEmail(token);
            } catch (Exception e) {
                // Token inválido ou expirado
                filterChain.doFilter(request, response);
                return;
            }

            if (email != null && 
                SecurityContextHolder.getContext().getAuthentication() == null) {
                
                User user = userRepository.findByEmail(email).orElse(null);

                if (user != null && jwtService.isAccessTokenValid(token, user)) {

                    UsernamePasswordAuthenticationToken authToken = 
                        new UsernamePasswordAuthenticationToken(
                        user, 
                        null,
                        user.getAuthorities()
                    );

                    authToken.setDetails( 
                        new WebAuthenticationDetailsSource()
                            .buildDetails(request)
                    );

                    SecurityContextHolder.getContext()
                        .setAuthentication(authToken);
                }
            }
        
        String type = jwtService.extractTokenType(token);
        if(!"access".equals(type)){
            filterChain.doFilter(request, response);
            return;
        }
    }

    
    
}
