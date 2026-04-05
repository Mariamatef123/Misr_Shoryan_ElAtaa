package com.misrshoryanelataa.misr_shoryan_elataa.Services;
import com.misrshoryanelataa.misr_shoryan_elataa.Repos.VolunteerRepo;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import com.misrshoryanelataa.misr_shoryan_elataa.Enums.InterviewStatus;
import com.misrshoryanelataa.misr_shoryan_elataa.Models.InterviewEntity;
import com.misrshoryanelataa.misr_shoryan_elataa.Models.InterviewSlotEntity;
import com.misrshoryanelataa.misr_shoryan_elataa.Models.VolunteerEntity;
import com.misrshoryanelataa.misr_shoryan_elataa.Repos.InterviewRepo;

@Service
public class VolunteerService {
    
    @Autowired
    private InterviewRepo interviewRepo;

    @Autowired
    private VolunteerRepo volunteerRepo;
    public List<InterviewEntity> getInterview() {
        return interviewRepo.findAll().stream()
                .collect(Collectors.toList());
    }

    public List<InterviewSlotEntity> getAvailableInterviewSlots() {
        return interviewRepo.findAll().stream()
                .flatMap(interview -> interview.getInterviewSlots().stream())
                .filter(slot -> slot.getStatus() == InterviewStatus.AVALIABLE)
                .collect(Collectors.toList());
    }

    public Object chooseInterviewSlot(InterviewSlotEntity slot) {
        InterviewSlotEntity interviewSlot = interviewRepo.findAll().stream()
                .flatMap(interview -> interview.getInterviewSlots().stream())
                .filter(s -> s.getSlotID() == slot.getSlotID())
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Interview slot not found"));

        if (interviewSlot.getStatus() != InterviewStatus.AVALIABLE) {
            throw new RuntimeException("Interview slot is not available");
        }
        interviewSlot.setStatus(InterviewStatus.UNAVALIABLE);
        InterviewEntity interview = new InterviewEntity();
        interviewRepo.save(interview);
        return "Interview slot booked successfully";
    }

    public void createVolunteer(VolunteerEntity volunteer) {
       if(volunteerRepo.existsByEmail(volunteer.getEmail())){
           throw new RuntimeException("Email already exists");
       }
       if(volunteer.getUniversityEmail().contains("@fcih.helwan.edu.eg")){
        throw new RuntimeException("must enter your university email");
       }
        volunteer.setAssignedDepartment(null);
        volunteerRepo.save(volunteer);
    }

}