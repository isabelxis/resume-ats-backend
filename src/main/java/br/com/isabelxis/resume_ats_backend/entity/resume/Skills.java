package br.com.isabelxis.resume_ats_backend.entity.resume;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "skills")
@Getter
@Setter
public class Skills {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;  
    
    @ManyToOne
    @JoinColumn(name = "skill_type_id", nullable = false)
    private SkillType skillType; //HARD, SOFT, LANGUAGES, TOOLS

    @ManyToOne
    @JoinColumn(name = "resume_id", nullable = false)
    private Resume resume;
    
}