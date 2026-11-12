package com.misrshoryanelataa.misr_shoryan_elataa.Models;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.misrshoryanelataa.misr_shoryan_elataa.Enums.InterviewStatus;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import jakarta.persistence.OneToOne;

@Entity
@Access(AccessType.FIELD) 
public class InterviewSlotEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "slot_id")
     Integer slotID;

    @OneToOne
    @JoinColumn(name = "volunteer_id", unique = true)
    @JsonBackReference("volunteer-slot")
    private VolunteerEntity volunteer;

    @ManyToOne
    @JoinColumn(name = "interview_id")
    @JsonBackReference("interview-slot")
    private InterviewEntity interview;

    @JsonProperty("interviewId")
    public Integer getInterviewId() {
        return interview != null ? interview.getId() : null;
    }

    @JsonFormat(pattern = "HH:mm")
    @Column(name = "slot_time")
    private LocalTime slotTime;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "slot_date")
    private LocalDate slotDate;

    @Enumerated(EnumType.STRING)
    private InterviewStatus status = InterviewStatus.AVAILABLE;

    // ✅ FIX: use Integer NOT int
    public Integer getSlotID() {
        return slotID;
    }

    public void setSlotID(Integer slotID) {
        this.slotID = slotID;
    }

    public VolunteerEntity getVolunteer() {
        return volunteer;
    }

    public void setVolunteer(VolunteerEntity volunteer) {
        this.volunteer = volunteer;

        if (volunteer != null && volunteer.getInterviewSlot() != this) {
            volunteer.setInterviewSlot(this);
        }
    }

    public InterviewEntity getInterview() {
        return interview;
    }

    public void setInterview(InterviewEntity interview) {
        this.interview = interview;
    }

    public LocalTime getSlotTime() {
        return slotTime;
    }

    public void setSlotTime(LocalTime slotTime) {
        this.slotTime = slotTime;
    }

    public LocalDate getSlotDate() {
        return slotDate;
    }

    public void setSlotDate(LocalDate slotDate) {
        this.slotDate = slotDate;
    }

    public InterviewStatus getStatus() {
        return status;
    }

    public void setStatus(InterviewStatus status) {
        this.status = status;
    }
}