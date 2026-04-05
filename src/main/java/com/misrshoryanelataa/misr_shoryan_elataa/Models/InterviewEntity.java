package com.misrshoryanelataa.misr_shoryan_elataa.Models;

import java.util.List;
import jakarta.persistence.*;

@Entity
public class InterviewEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "interview", cascade = CascadeType.ALL)
    private List<InterviewSlotEntity> interviewSlots;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<InterviewSlotEntity> getInterviewSlots() {
        return interviewSlots;
    }

    public void setInterviewSlots(List<InterviewSlotEntity> interviewSlots) {
        this.interviewSlots = interviewSlots;
    }

    public InterviewEntity() {
    }
}