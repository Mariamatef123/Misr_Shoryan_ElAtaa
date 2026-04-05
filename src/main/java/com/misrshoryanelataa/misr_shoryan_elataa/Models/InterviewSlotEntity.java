package com.misrshoryanelataa.misr_shoryan_elataa.Models;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.misrshoryanelataa.misr_shoryan_elataa.Enums.InterviewStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class InterviewSlotEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @JsonManagedReference
    @OneToOne
    @JoinColumn(name = "volunteer_id", unique = true)
    private VolunteerEntity volunteer;

@JsonBackReference
@ManyToOne
@JoinColumn(name = "interview_id")
private InterviewEntity interview;

@JsonProperty("interviewId")
public int getInterviewId() {
    return interview != null ? interview.getId() : null;

}
@JsonFormat(pattern = "HH:mm")
private LocalTime slotTime;

@JsonFormat(pattern = "yyyy-MM-dd")
private LocalDate slotDate;

@Enumerated(EnumType.STRING)
private InterviewStatus status=InterviewStatus.AVAILABLE;


    public void setStatus(InterviewStatus status) {
        this.status = status;
    }

    public InterviewStatus getStatus() {
        return status;
    }

    public void setSlotID(int slotID) {
        this.id = slotID;
    }

    public void setSlotDate(LocalDate slotDate) {
        this.slotDate = slotDate;
    }

    public void setSlotTime(LocalTime slotTime) {
        this.slotTime = slotTime;
    }

    public int getSlotID() {
        return id;
    }

    public LocalDate getSlotDate() {
        return slotDate;
    }

    public LocalTime getSlotTime() {
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

    public void setInterview(InterviewEntity interview) {
        this.interview = interview;
    }

    public InterviewEntity getInterview() {
        return interview;
    }

    public InterviewSlotEntity() {
    }

    public static Collection<InterviewSlotEntity> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }
}