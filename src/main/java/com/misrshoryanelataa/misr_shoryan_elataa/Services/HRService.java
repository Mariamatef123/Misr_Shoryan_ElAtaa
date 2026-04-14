package com.misrshoryanelataa.misr_shoryan_elataa.Services;

import com.misrshoryanelataa.misr_shoryan_elataa.Enums.InterviewStatus;
import com.misrshoryanelataa.misr_shoryan_elataa.Enums.Role;
import com.misrshoryanelataa.misr_shoryan_elataa.Enums.volunteerStatus;
import com.misrshoryanelataa.misr_shoryan_elataa.Models.*;
import com.misrshoryanelataa.misr_shoryan_elataa.Repos.*;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class HRService {


    @Autowired
    InterviewRepo interviewRepo;

    @Autowired
    HrRepo hrRepo;

    @Autowired
    InterviewSlotsRepo interviewSlotRepo;

    @Autowired
    StaffRepo staffRepo;

    @Autowired
    VolunteerRepo volunteerRepo;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    PrRepo prRepo;

    private boolean isAdmin(int hrId) {
        return hrRepo.findById(hrId)
                .map(hr -> hr.getIsAdmin())
                .orElse(false);
    }
    // interview,interview slot-----------------
    public void addInterviewSlot(InterviewSlotEntity interviewSlot, int interviewId) {

        InterviewEntity interview = interviewRepo.findById(interviewId)
                .orElseThrow(() -> new RuntimeException("Interview not found"));

        interviewSlot.setInterview(interview);
        interviewSlot.setStatus(InterviewStatus.AVAILABLE);
        interviewSlot.setVolunteer(null);


        interviewSlotRepo.save(interviewSlot);
    }
    public void createInterview(int hrId, InterviewEntity interview) {

        if (!isAdmin(hrId)) {
            throw new RuntimeException("Only admins can create interviews");
        }

        HREntity hr = hrRepo.findById(hrId)
                .orElseThrow(() -> new RuntimeException("HR not found"));

        interview.setHr(hr);
        interviewRepo.save(interview);
        hr.getInterview().add(interview);

        hrRepo.save(hr);
    }

    public List<InterviewSlotEntity> getAllInterviewSlots() {
        return interviewSlotRepo.findAll();
    }

    public void deleteInterviewSlot(int id, int hrId) {

        if (!isAdmin(hrId)) {
            throw new RuntimeException("Only admins can delete interview slots");
        }

        InterviewSlotEntity slot = interviewSlotRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Slot not found"));

        InterviewEntity interview = slot.getInterview();
        if (interview != null) {
            interview.getInterviewSlots().remove(slot);
            interviewRepo.save(interview);
        }
        interviewSlotRepo.delete(slot);
    }

    public void updateInterviewSlot(int id, InterviewSlotEntity interviewSlot, int hrId) {

        if (!isAdmin(hrId)) {
            throw new RuntimeException("Only admins can update interview slots");
        }

        interviewSlotRepo.findById(id).ifPresent(slot -> {

            slot.setVolunteer(interviewSlot.getVolunteer());
            slot.setInterview(interviewSlot.getInterview());

            interviewSlotRepo.save(slot);

            if (slot.getInterview() != null) {
                interviewRepo.findById(slot.getInterview().getId())
                        .ifPresent(interview -> {
                            interview.getInterviewSlots().add(slot);
                            interviewRepo.save(interview);
                        });
            }
        });
    }

//------------

    // Staff-----------------
    public List<? extends StaffEntity> getAllStaff(int hrId) {
        HREntity hr = hrRepo.findById(hrId)
                .orElseThrow(() -> new RuntimeException("HR not found"));
        if (hr.getIsAdmin()) {
            return staffRepo.findAll();
        }
        return staffRepo.findAll()
                .stream()
                .filter(staff -> !(staff.getRole() == Role.HR_ADMIN || staff.getRole() == Role.HR_MEMBER))
                .toList();
    }

    public void deleteStaff(int staffId, int hrId) {
        HREntity requester = hrRepo.findById(hrId)
                .orElseThrow(() -> new RuntimeException("HR not found"));
        StaffEntity staffToDelete = staffRepo.findById(staffId)
                .orElseThrow(() -> new RuntimeException("Staff not found"));
        if (!requester.getIsAdmin() && staffToDelete instanceof HREntity) {
            throw new RuntimeException("Only admins can delete HR staff");
        }
        staffRepo.delete(staffToDelete);
    }

    public void updateStaff(int staffId, StaffEntity newData, int hrId) {
        HREntity requester = hrRepo.findById(hrId)
                .orElseThrow(() -> new RuntimeException("HR not found"));

        StaffEntity existingStaff = staffRepo.findById(staffId)
                .orElseThrow(() -> new RuntimeException("Staff not found"));

        if (!requester.getIsAdmin() && existingStaff.getRole() == Role.HR_ADMIN||existingStaff.getRole() == Role.HR_MEMBER) {
            throw new RuntimeException("Only admins can update HR");
        }
        if (staffRepo.existsByEmail(newData.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        existingStaff.setName(newData.getName());
        existingStaff.setEmail(newData.getEmail());
        existingStaff.setRole(newData.getRole());
          existingStaff.setPhoneNumber(newData.getPhoneNumber());
        existingStaff.setPassword(newData.getPassword());
        staffRepo.save(existingStaff);
    }

    public void createStaff(StaffEntity staff, int hrId) {

        HREntity requester = hrRepo.findById(hrId)
                .orElseThrow(() -> new RuntimeException(" HR not found"));

        if (!requester.getIsAdmin() && (staff.getRole() == Role.HR_ADMIN||staff.getRole() == Role.HR_MEMBER)) {
            throw new RuntimeException("Only admins can create HR");
        }
        if (staffRepo.existsByEmail(staff.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        if (staff.getRole() == Role.HR_ADMIN || staff.getRole() == Role.HR_MEMBER) {

            HREntity newHr = new HREntity();
            newHr.setName(staff.getName());
            newHr.setPhoneNumber(staff.getPhoneNumber());
            newHr.setEmail(staff.getEmail());
            newHr.setPassword(staff.getPassword());
            newHr.setOfficialEmail(staff.getOfficialEmail());
            newHr.setRole(staff.getRole());
            newHr.setIsAdmin(staff.getRole() == Role.HR_ADMIN);

            hrRepo.save(newHr);
            return;
        }

        if (staff.getRole() == Role.PR_ADMIN) {
            PREntity newPr = new PREntity();
            newPr.setName(staff.getName());
            newPr.setEmail(staff.getEmail());
            newPr.setPassword(staff.getPassword());
            newPr.setPhoneNumber(staff.getPhoneNumber());
            newPr.setOfficialEmail(staff.getOfficialEmail());
            newPr.setRole(staff.getRole());
            newPr.setIsAdmin(staff.getRole() == Role.PR_ADMIN);
            prRepo.save(newPr);
            return;
        }

        staffRepo.save(staff);
    }
//-------------------------

    public List<VolunteerEntity> getAllVolunteers(int hrId) {

        HREntity hr = hrRepo.findById(hrId)
                .orElseThrow(() -> new RuntimeException("HR not found"));
        if(hr.getIsAdmin()){
            return volunteerRepo.findAll();
        }
        else{

            return volunteerRepo.findAll()
                    .stream()
                    .filter(volunteer -> volunteer.getHr() != null && volunteer.getHr().getId() == hrId)
                    .collect(Collectors.toList());
        }

        // if (!hrRepo.findAll().stream().filter(hr -> hr.getId() == hrId).findFirst().map(hr -> hr.getIsAdmin())
        //         .orElse(false)) {
        //     throw new RuntimeException("Only admins can get volunteers");
        // }
        // return volunteerRepo.findAll();
    }

   public List<Object> getVolunteersEditable(int hrId){
            HREntity hr = hrRepo.findById(hrId)
                .orElseThrow(() -> new RuntimeException("HR not found"));
                        if(!hr.getIsAdmin()){
                LocalDate today = LocalDate.now();
             
       return interviewSlotRepo.findAll().stream()
                .filter(slot -> slot.getSlotDate().isBefore(today) && slot.getVolunteer() != null && slot.getVolunteer().getHr() != null && slot.getVolunteer().getHr().getId() == hrId)
                .map(slot -> {
                    VolunteerEntity volunteer = slot.getVolunteer();
                    
                    if (!hr.getVolunteers().contains(volunteer)) {
                        hr.getVolunteers().add(volunteer);
                    }
                    return volunteer;
                }).collect(Collectors.toList());}
                else{
                    return new ArrayList<>();
                }
   }

    public void assignVolunteerToHR(int assignerHrId, int volunteerId, int targetHrId) {

        HREntity assigner = hrRepo.findById(assignerHrId)
                .orElseThrow(() -> new RuntimeException("HR not found"));

        if (!assigner.getIsAdmin()) {
            throw new RuntimeException("Only admins can assign volunteers");
        }

        VolunteerEntity volunteer = volunteerRepo.findById(volunteerId)
                .orElseThrow(() -> new RuntimeException("Volunteer not found"));

        HREntity targetHr = hrRepo.findById(targetHrId)
                .orElseThrow(() -> new RuntimeException("Target HR not found"));

        targetHr.getVolunteers().add(volunteer);
        volunteer.setHr(targetHr);

        hrRepo.save(targetHr);
        volunteerRepo.save(volunteer);
    }

    public void sendEmailToVolunteer(int volunteerId, int hrId) {
        HREntity hr = hrRepo.findById(hrId)
                .orElseThrow(() -> new RuntimeException("HR not found"));

        VolunteerEntity volunteer = volunteerRepo.findById(volunteerId)
                .orElseThrow(() -> new RuntimeException("Volunteer not found"));

        String toEmail = volunteer.getEmail();
        String subject = "Update on Your Volunteer Application";
        String emailContent;
        Role assignedDepartment = volunteer.getAssignedDepartment();

        if (volunteer.getStatus() == volunteerStatus.REJECTED) {
            emailContent = "Dear " + volunteer.getName() +
                    ",\n\nWe regret to inform you that your application has been rejected.";

        } else {
            if (assignedDepartment == Role.PR_ADMIN ||
                    assignedDepartment == Role.LEP ||
                    assignedDepartment == Role.HR_ADMIN ||
                    assignedDepartment == Role.HR_MEMBER) {

                String cleanName = volunteer.getName().toLowerCase().replaceAll("\\s+", "");
                String generated;

                do {
                    generated = cleanName
                            + volunteer.getId()
                            + new Random().nextInt(1000)
                            + "@misrshoryanelataa.com";
                } while (staffRepo.existsByEmail(generated));

                String emailGenerated = generated;
                String passwordGenerated = UUID.randomUUID().toString().substring(0, 8);
                StaffEntity staff = new StaffEntity();
                staff.setName(volunteer.getName());
                staff.setEmail(volunteer.getEmail());
                staff.setOfficialEmail(emailGenerated);
                staff.setPassword(passwordGenerated);
                staff.setRole(assignedDepartment);
                staffRepo.save(staff);

                emailContent = "Dear " + volunteer.getName() +
                        ",\n\nCongratulations! Your application has been accepted.\n" +
                        "You have been assigned to the " + assignedDepartment + " department.\n\n" +
                        "Your email is: " + emailGenerated + "\n" +
                        "Your password is: " + passwordGenerated + "\n\n" +
                        "Please use these credentials to log in to the volunteer portal.";

            } else {
                emailContent = "Dear " + volunteer.getName() +
                        ",\n\nCongratulations! Your application has been accepted.\n" +
                        "You have been assigned to the " + assignedDepartment + " department.\n\n";
            }
        }

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(emailContent);
        message.setFrom("misrshoryanelataa@gmail.com");
        mailSender.send(message);
    }
    public List<VolunteerEntity> getVolunteersAssignedToHR(int hrId) {
        HREntity hr = hrRepo.findById(hrId)
                .orElseThrow(() -> new RuntimeException("HR not found"));
        return hr.getVolunteers().stream()
                .filter(volunteer -> volunteer.getHr() != null && volunteer.getHr().getId() == hrId)
                .collect(Collectors.toList());
    }

    public void acceptVolunteer(int volunteerId, int hrId, Role assignedDepartment) {
        HREntity hr = hrRepo.findById(hrId)
                .orElseThrow(() -> new RuntimeException("HR not found"));

        VolunteerEntity volunteer = volunteerRepo.findById(volunteerId)
                .orElseThrow(() -> new RuntimeException("Volunteer not found"));

        volunteer.setStatus(volunteerStatus.ACCEPTED);
        volunteer.setAssignedDepartment(assignedDepartment);
        volunteerRepo.save(volunteer);
        sendEmailToVolunteer(volunteerId, hrId);
    }

    public void chooseDepartmentForVolunteer(int volunteerId, int hrId, Role department) {
        HREntity hr = hrRepo.findById(hrId)
                .orElseThrow(() -> new RuntimeException("HR not found"));

        VolunteerEntity volunteer = volunteerRepo.findById(volunteerId)
                .orElseThrow(() -> new RuntimeException("Volunteer not found"));

        if (!hr.getVolunteers().contains(volunteer)) {
            throw new RuntimeException("Volunteer is not assigned to this HR");
        }
        if (!hr.getIsAdmin() && volunteer.getStatus() == volunteerStatus.ACCEPTED) {
            volunteer.setAssignedDepartment(department);
            volunteerRepo.save(volunteer);
        }
    }

    public void rejectVolunteer(int volunteerId, int hrId) {
        HREntity hr = hrRepo.findById(hrId)
                .orElseThrow(() -> new RuntimeException("HR not found"));

        VolunteerEntity volunteer = volunteerRepo.findById(volunteerId)
                .orElseThrow(() -> new RuntimeException("Volunteer not found"));

        volunteer.setStatus(volunteerStatus.REJECTED);
        volunteerRepo.save(volunteer);

        if (!hr.getIsAdmin()) {
            sendEmailToVolunteer(volunteerId, hrId);
            interviewSlotRepo.clearVolunteerReference(volunteerId);
            volunteerRepo.delete(volunteer);
        }
    }
}