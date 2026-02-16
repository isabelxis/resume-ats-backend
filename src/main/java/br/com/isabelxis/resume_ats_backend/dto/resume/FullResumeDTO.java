package br.com.isabelxis.resume_ats_backend.dto.resume;

import java.util.List;

import br.com.isabelxis.resume_ats_backend.dto.user.ProfileDTO;
import br.com.isabelxis.resume_ats_backend.dto.user.experience.ListExperienceDTO;

public record FullResumeDTO(
    Long id,
    String title,
    String summary,

    ProfileDTO profile,

    List<ListExperienceDTO> experiences

   // List<ListEducationDTO> education
) {
    

        
}
