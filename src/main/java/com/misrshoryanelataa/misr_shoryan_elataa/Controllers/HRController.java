package com.misrshoryanelataa.misr_shoryan_elataa.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.misrshoryanelataa.misr_shoryan_elataa.Enums.Role;
import com.misrshoryanelataa.misr_shoryan_elataa.Models.InterviewEntity;
import com.misrshoryanelataa.misr_shoryan_elataa.Models.InterviewSlotEntity;
import com.misrshoryanelataa.misr_shoryan_elataa.Models.StaffEntity;
import com.misrshoryanelataa.misr_shoryan_elataa.Models.VolunteerEntity;
import com.misrshoryanelataa.misr_shoryan_elataa.Services.HRService;

@RestController
public class HRController {
    @Autowired
    private HRService hrService;

    @PostMapping("/create/{hrId}")
    public ResponseEntity<String> createInterview(
            @PathVariable int hrId,
            @RequestBody InterviewEntity interview) {

        hrService.createInterview(hrId, interview);
        return ResponseEntity.ok("Interview created successfully");
    }

    @PostMapping("/{interviewId}/slots")
    public ResponseEntity<String> addInterviewSlot(
            @PathVariable int interviewId,
            @RequestBody InterviewSlotEntity slot) {

        hrService.addInterviewSlot(slot, interviewId);
        return ResponseEntity.ok("Interview slot added successfully");
    }

    @GetMapping("/slots/{hrId}")
    public ResponseEntity<List<InterviewSlotEntity>> getAllInterviewSlots(
            @PathVariable int hrId) {

        return ResponseEntity.ok(hrService.getAllInterviewSlots(hrId));
    }

    @DeleteMapping("/slots/{hrId}/{slotId}")
    public ResponseEntity<String> deleteInterviewSlot(
            @PathVariable int hrId,
            @PathVariable int slotId) {

        hrService.deleteInterviewSlot(slotId, hrId);
        return ResponseEntity.ok("Interview slot deleted successfully");
    }

    @PutMapping("/slots/{hrId}/{slotId}")
    public ResponseEntity<String> updateInterviewSlot(
            @PathVariable int hrId,
            @PathVariable int slotId,
            @RequestBody InterviewSlotEntity slot) {

        hrService.updateInterviewSlot(slotId, slot, hrId);
        return ResponseEntity.ok("Interview slot updated successfully");
    }

    @GetMapping("/volunteers")
    public List<VolunteerEntity> getAllVolunteers(int hrId) {
        return hrService.getAllVolunteers(hrId);
    }


//staff-----------------
    @GetMapping("/staff")
    public List<? extends StaffEntity> getStaff(@RequestParam int hrId) {

        return hrService.getAllStaff(hrId);
    }

    @DeleteMapping("/staff/{id}")
    public ResponseEntity<String> deleteStaff(@PathVariable int id,@RequestParam int hrId) {
       try{
            hrService.deleteStaff(id, hrId);
            return ResponseEntity.ok("Staff deleted successfully");
       } catch (RuntimeException ex) {
               return ResponseEntity
                    .badRequest()
                    .body(ex.getMessage());
        }
    }
       
    @PutMapping("/staff/{id}")
    public ResponseEntity<String> updateStaff(@PathVariable int id, @RequestBody StaffEntity staff,@RequestParam int hrId) {
      
        try{
                hrService.updateStaff(id, staff, hrId);
                return ResponseEntity.ok("Staff updated successfully");
        } catch (RuntimeException ex) {
            return ResponseEntity
                    .badRequest()
                    .body(ex.getMessage());
        }
    }
@PostMapping("/staff/{hrId}")
public ResponseEntity<String> createStaff(
        @RequestBody StaffEntity staff,
        @PathVariable int hrId) {

    try {
        hrService.createStaff(staff, hrId);
        return ResponseEntity.ok("Staff created successfully");

    } catch (RuntimeException ex) {
        return ResponseEntity
                .badRequest()
                .body(ex.getMessage());
    }
}

//-------------------------------


//volunteer-----------------
    @PostMapping("/assign-volunteer/{volId}")
    public String assignVolunteer(
            @RequestParam int assignerHrId,
            @PathVariable int volunteerId,
            @RequestParam int targetHrId) {

        hrService.assignVolunteerToHR(assignerHrId, volunteerId, targetHrId);
        return "Volunteer assigned successfully";
    }

   @PostMapping("/send-email")
    public void sendEmailToVolunteer(
            @RequestParam int volunteerId,
            @RequestParam int hrId) {
        hrService.sendEmailToVolunteer(volunteerId, hrId);
    }

    @GetMapping("/volunteerAssignedToHr/{hrId}")
    public List<VolunteerEntity> getVolunteersAssignedToHR(@PathVariable int hrId) {
        return hrService.getVolunteersAssignedToHR(hrId);
    }
    @PostMapping("/accept-volunteer")
    public void acceptVolunteer(@RequestParam int volunteerId, @RequestParam int hrId, Role assignedDepartment) {
        hrService.acceptVolunteer(volunteerId, hrId, assignedDepartment);
    }

    @PostMapping("/reject-volunteer")
    public void rejectVolunteer(@RequestParam int volunteerId, @RequestParam int hrId) {
        hrService.rejectVolunteer(volunteerId, hrId);
    }

    @PostMapping("/choose-dept")
    public void chooseDepartmentForVolunteer(@RequestParam int volunteerId, @RequestParam int hrId,
            @RequestBody Role assignedDepartment) {

        hrService.chooseDepartmentForVolunteer(volunteerId, hrId, assignedDepartment);
        ;
    }
//----------------------------
}