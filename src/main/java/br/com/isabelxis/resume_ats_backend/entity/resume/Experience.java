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
@Table(name = "experiences")
@Getter
@Setter
public class Experience {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String company;
    private String position;
    private String description;
    private String startDate;
    private String endDate;
    private String skills;
    private String models; //REMOTE, HYBRID, ONSITE

    @ManyToOne
    @JoinColumn(name = "resume_id")
    private Resume resume;

}
