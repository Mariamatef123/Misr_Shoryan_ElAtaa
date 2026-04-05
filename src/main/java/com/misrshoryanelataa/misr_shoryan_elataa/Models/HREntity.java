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
    private List<InterviewSlotEntity> interviewSlots;
public Boolean getIsAdmin() {
    return isAdmin;
}
public void setInterviewSlots(InterviewSlotEntity interviewSlot) {
    this.interviewSlots.add(interviewSlot);
}
public List<InterviewSlotEntity> getInterviewSlots() {
    return interviewSlots;
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