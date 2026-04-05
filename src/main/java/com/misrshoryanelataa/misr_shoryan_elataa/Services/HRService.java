package com.misrshoryanelataa.misr_shoryan_elataa.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.misrshoryanelataa.misr_shoryan_elataa.Models.InterviewSlotEntity;
import com.misrshoryanelataa.misr_shoryan_elataa.Models.VolunteerEntity;
import com.misrshoryanelataa.misr_shoryan_elataa.Repos.HrRepo;
import com.misrshoryanelataa.misr_shoryan_elataa.Repos.InterviewSlotsRepo;

@Service
public class HRService {
    @Autowired
    HrRepo hrRepo;

    @Autowired
    InterviewSlotsRepo interviewSlotRepo;

    public void createInterviewSlot(int hrId, InterviewSlotEntity interviewSlot) {
        if(!hrRepo.findAll().stream().filter(hr -> hr.getId() == hrId).findFirst().map(hr -> hr.getIsAdmin()).orElse(false)) {
            throw new RuntimeException("Only admins can create interview slots");
        }
        hrRepo.findAll().stream().filter(hr -> hr.getId() == hrId).findFirst().ifPresent(hr -> {
            interviewSlot.setHr(hr);
            hr.getInterviewSlots().add(interviewSlot);
            interviewSlotRepo.save(interviewSlot);
            hrRepo.save(hr);
        });
    }

    public List<InterviewSlotEntity> getAllInterviewSlots(int hrId) {
         if(!hrRepo.findAll().stream().filter(hr -> hr.getId() == hrId).findFirst().map(hr -> hr.getIsAdmin()).orElse(false)) {
            throw new RuntimeException("Only admins can get interview slots");
        }
        return hrRepo.findAll().stream().flatMap(hr -> hr.getInterviewSlots().stream()).toList();
    }

    public void deleteInterviewSlot(int id,int hrId) {
     if(!hrRepo.findAll().stream().filter(hr -> hr.getId() == hrId).findFirst().map(hr -> hr.getIsAdmin()).orElse(false)) {
            throw new RuntimeException("Only admins can detete interview slots");
    }
        hrRepo.findAll().forEach(hr -> {
            hr.getInterviewSlots().removeIf(slot -> slot.getSlotID() == id);
            interviewSlotRepo.deleteById(id);
            hrRepo.save(hr);
        });
    }

    public void updateInterviewSlot(int id, InterviewSlotEntity interviewSlot,int hrId) {
     if(!hrRepo.findAll().stream().filter(hr -> hr.getId() == hrId).findFirst().map(hr -> hr.getIsAdmin()).orElse(false)) {
            throw new RuntimeException("Only admins can update interview slots");
        }
        hrRepo.findAll().forEach(hr -> {
            hr.getInterviewSlots().removeIf(slot -> slot.getSlotID() == id);
            interviewSlot.setHr(hr);
            hr.getInterviewSlots().add(interviewSlot);
            interviewSlotRepo.save(interviewSlot);
            hrRepo.save(hr);
        });
    }

    public List<VolunteerEntity> getAllVolunteers(int hrId) {
         if(!hrRepo.findAll().stream().filter(hr -> hr.getId() == hrId).findFirst().map(hr -> hr.getIsAdmin()).orElse(false)) {
            throw new RuntimeException("Only admins can get volunteers");
        }
        return hrRepo.findAll().stream().flatMap(hr -> hr.getVolunteers().stream()).toList();
    }



    public void assignVolunteerToHR(int assignerHrId, int volunteerId, int targetHrId) {
            if(!hrRepo.findAll().stream().filter(hr -> hr.getId() == assignerHrId).findFirst().map(hr -> hr.getIsAdmin()).orElse(false)) {
                throw new RuntimeException("Only admins can assign volunteers");
            }
            VolunteerEntity volunteer = hrRepo.findAll().stream()
                    .flatMap(hr -> hr.getVolunteers().stream())
                    .filter(v -> v.getId() == volunteerId)
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Volunteer not found"));
    
            hrRepo.findAll().forEach(hr -> {
                if (hr.getId() == targetHrId) {
                    hr.getVolunteers().add(volunteer);
                    volunteer.setHr(hr);
                    hrRepo.save(hr);
                } else {
                    hr.getVolunteers().removeIf(v -> v.getId() == volunteerId);
                    hrRepo.save(hr);
                }
            });
    }

}