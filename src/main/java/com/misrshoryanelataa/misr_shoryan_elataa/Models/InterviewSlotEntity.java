package com.misrshoryanelataa.misr_shoryan_elataa.Models;

import java.sql.Time;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.misrshoryanelataa.misr_shoryan_elataa.Enums.InterviewStatus;

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
public class InterviewSlotEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "volunteer_id", unique = true)
    private VolunteerEntity volunteer;

    @ManyToOne
    @JoinColumn(name = "hr_id")
    private HREntity hr;

    @ManyToOne
    @JoinColumn(name = "interview_id")
    private InterviewEntity interview;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date slotDate;

    @DateTimeFormat(pattern = "HH:mm")
    private Time slotTime;

    @Enumerated(EnumType.STRING)
    private InterviewStatus status;

    public void setHr(HREntity hr) {
        this.hr = hr;
    }

    public HREntity getHr() {
        return hr;
    }

    public void setStatus(InterviewStatus status) {
        this.status = status;
    }

    public InterviewStatus getStatus() {
        return status;
    }

    public void setSlotID(int slotID) {
        this.id = slotID;
    }

    public void setSlotDate(Date slotDate) {
        this.slotDate = slotDate;
    }

    public void setSlotTime(Time slotTime) {
        this.slotTime = slotTime;
    }

    public int getSlotID() {
        return id;
    }

    public Date getSlotDate() {
        return slotDate;
    }

    public Time getSlotTime() {
        return slotTime;
    }

    public void setVolunteer(VolunteerEntity volunteer) {
        this.volunteer = volunteer;
    }

    public VolunteerEntity getVolunteer() {
        return volunteer;
    }

    public void setInterviewSlotID(int interviewSlotID) {
        this.id = interviewSlotID;
    }

    public int getInterviewSlotID() {
        return id;
    }

    public InterviewSlotEntity() {
    }
}