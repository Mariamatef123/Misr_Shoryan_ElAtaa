package com.misrshoryanelataa.misr_shoryan_elataa.Models;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.misrshoryanelataa.misr_shoryan_elataa.Enums.Role;
import com.misrshoryanelataa.misr_shoryan_elataa.Enums.volunteerStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class VolunteerEntity extends UserEntity {
    @Column(name="UniversityEmail")
    String universityEmail;




@JsonManagedReference
@ManyToOne(fetch = FetchType.EAGER)
@JoinColumn(name = "hr_id")
private HREntity hr;

    @JsonBackReference
    @OneToOne
    @JoinColumn(name = "interview_slot_id")
    private InterviewSlotEntity interviewSlot;


@JsonProperty("interview_slot_date")
public LocalDate getInterviewSlotDate() {
    return interviewSlot != null ? interviewSlot.getSlotDate() : null;
}


    @Enumerated(EnumType.STRING)
    private volunteerStatus status = volunteerStatus.PENDING;

    @Enumerated(EnumType.STRING)
    Role AssignedDepartment;

    String phoneNumber;
    public void setAssignedDepartment(Role assignedDepartment) {
        AssignedDepartment = assignedDepartment;
    }

    public Role getAssignedDepartment() {
        return AssignedDepartment;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setUniversityEmail(String universityEmail) {
        this.universityEmail = universityEmail;
    }

    public String getUniversityEmail() {
        return universityEmail;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setHr(HREntity hr) {
        this.hr = hr;
    }

    public HREntity getHr() {
        return hr;

    }

    public void setInterviewSlot(InterviewSlotEntity interviewSlot) {

        this.interviewSlot = interviewSlot;
    }

    public InterviewSlotEntity getInterviewSlot() {
        return interviewSlot;
    }

    public void setStatus(volunteerStatus status) {
        this.status = status;
    }

    public volunteerStatus getStatus() {
        return status;
    }

    public VolunteerEntity() {

    }
}