package com.misrshoryanelataa.misr_shoryan_elataa.Models;



import jakarta.validation.constraints.Email;

import com.misrshoryanelataa.misr_shoryan_elataa.Enums.Role;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class VolunteerEntity extends UserEntity {
   
   @Email
   String UniversityEmail;

@ManyToOne
    @JoinColumn(name = "hr_id")
    private HREntity hr;

    @OneToOne
    @JoinColumn(name = "interview_slot_id")
    private InterviewSlotEntity interviewSlot;
   String phoneNumber;
   @Enumerated(EnumType.STRING)
   Role AssignedDepartment;
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
    UniversityEmail = universityEmail;
}
public String getUniversityEmail() {
    return UniversityEmail;
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

    public VolunteerEntity() {
       
    }
}