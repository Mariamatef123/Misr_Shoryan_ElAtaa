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

    @ManyToOne
    @JoinColumn(name = "hr_id")
    private HREntity hr;

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

    public void setHr(HREntity hr) {
        this.hr = hr;
    }

    public HREntity getHr() {
        return hr;
    }

    public InterviewEntity() {
    }
}