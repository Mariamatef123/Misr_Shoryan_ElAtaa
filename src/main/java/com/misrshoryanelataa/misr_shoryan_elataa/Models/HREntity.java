package com.misrshoryanelataa.misr_shoryan_elataa.Models;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

@Entity
public class HREntity extends StaffEntity {
    Boolean isAdmin;

    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    @OneToMany(mappedBy = "hr")
    private List<VolunteerEntity> volunteers;

    @OneToMany(mappedBy = "hr")
    private List<InterviewEntity> interviews;

    public Boolean getIsAdmin() {
        return isAdmin;
    }

    public void setInterview(InterviewEntity interview) {
        this.interviews.add(interview);
    }

    public List<InterviewEntity> getInterview() {
        return interviews;
    }

    public void setVolunteers(VolunteerEntity volunteer) {
        this.volunteers.add(volunteer);
    }

    public List<VolunteerEntity> getVolunteers() {
        return volunteers;
    }

    public HREntity() {

    }
}