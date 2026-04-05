package com.misrshoryanelataa.misr_shoryan_elataa.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.misrshoryanelataa.misr_shoryan_elataa.Models.InterviewSlotEntity;
import com.misrshoryanelataa.misr_shoryan_elataa.Models.VolunteerEntity;
import com.misrshoryanelataa.misr_shoryan_elataa.Services.HRService;

import jakarta.persistence.criteria.CriteriaBuilder.In;

@RestController
public class HRController {
   @Autowired
    private HRService hrService;
       @PostMapping("/interview-slots")
        public void createInterviewSlot(int hrId,@RequestBody InterviewSlotEntity interviewSlot) {
            hrService.createInterviewSlot(hrId, interviewSlot);
        }
    
        @GetMapping("/interview-slots")
        public List<InterviewSlotEntity> getAllInterviewSlots(int hrId) {
        return  hrService.getAllInterviewSlots(hrId);
        }
        
        @DeleteMapping("/interview-slots/{id}")
        public void deleteInterviewSlot(@PathVariable int id,int hrId) {
            hrService.deleteInterviewSlot(id, hrId);
        }
       
        @PutMapping("/interview-slots/{id}")
        public void updateInterviewSlot(@PathVariable int id,@RequestBody InterviewSlotEntity interviewSlot,int hrId) {
           hrService.updateInterviewSlot(id, interviewSlot, hrId);
        }
    
        @GetMapping("/volunteers")
        public List<VolunteerEntity> getAllVolunteers(int hrId) {
          return  hrService.getAllVolunteers(hrId);
        }
    
       @PostMapping("/assign-volunteer")
    public String assignVolunteer(
            @RequestParam int assignerHrId,
            @RequestParam int volunteerId,
            @RequestParam int targetHrId) {

        hrService.assignVolunteerToHR(assignerHrId, volunteerId, targetHrId);

        return "Volunteer assigned successfully";
    }

        // public void getAllHRs() {
        //
        // }
    
        // public void deleteHR() {
        //    
        // }
    
        // public void updateHR() {
        // 
        // }
}