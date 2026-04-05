package com.misrshoryanelataa.misr_shoryan_elataa.Models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;

@Entity
public class InterviewEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

@JsonManagedReference
@OneToMany(mappedBy = "interview", cascade = CascadeType.ALL, orphanRemoval = true)
private List<InterviewSlotEntity> interviewSlots = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "hr_id")
    private HREntity hr;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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