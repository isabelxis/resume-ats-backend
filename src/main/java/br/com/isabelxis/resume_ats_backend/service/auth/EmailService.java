package br.com.isabelxis.resume_ats_backend.service.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailService {
    
    private final JavaMailSender mailSender;

    @Value("${app.frontend.url}")
    private String frontendUrl;

    public void sendPasswordResetEmail(String to, String token) {
        
        try {
            String resetLink = frontendUrl + "/reset-password?token=" + token;

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = 
                    new MimeMessageHelper(message, true);

            helper.setTo(to);
            helper.setSubject("Redefinição de senha");
            helper.setText(buildEmailTemplate(resetLink), true);

            mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao enviar email de redefinição de senha:" + e.getMessage());
        }
    }

    private String buildEmailTemplate(String resetLink) {
        return """
                <html>
                <body style="font-family: Arial, sans-serif;">
                    <h2>Redefinição de senha</h2>
                    <p>Clique no botão abaixo para redefinir sua senha:</p>
                    <a href="%s"
                       style="
                         display:inline-block;
                         padding:12px 20px;
                         background-color:black;
                         color:white;
                         text-decoration:none;
                         border-radius:5px;">
                         Redefinir Senha
                    </a>
                    <p>O link expira em 15 minutos.</p>
                </body>
                </html>
                """.formatted(resetLink);
    }


}
