package com.misrshoryanelataa.misr_shoryan_elataa.Services;
import com.misrshoryanelataa.misr_shoryan_elataa.Repos.InterviewRepo;
import com.misrshoryanelataa.misr_shoryan_elataa.Repos.PrRepo;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import com.misrshoryanelataa.misr_shoryan_elataa.Enums.Role;
import com.misrshoryanelataa.misr_shoryan_elataa.Enums.volunteerStatus;
import com.misrshoryanelataa.misr_shoryan_elataa.Models.HREntity;
import com.misrshoryanelataa.misr_shoryan_elataa.Models.InterviewEntity;
import com.misrshoryanelataa.misr_shoryan_elataa.Models.InterviewSlotEntity;
import com.misrshoryanelataa.misr_shoryan_elataa.Models.PREntity;
import com.misrshoryanelataa.misr_shoryan_elataa.Models.StaffEntity;
import com.misrshoryanelataa.misr_shoryan_elataa.Models.VolunteerEntity;
import com.misrshoryanelataa.misr_shoryan_elataa.Repos.HrRepo;
import com.misrshoryanelataa.misr_shoryan_elataa.Repos.InterviewSlotsRepo;
import com.misrshoryanelataa.misr_shoryan_elataa.Repos.StaffRepo;
import com.misrshoryanelataa.misr_shoryan_elataa.Repos.VolunteerRepo;

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

    public void addInterviewSlot(InterviewSlotEntity interviewSlot, int interviewId) {

        InterviewEntity interview = interviewRepo.findById(interviewId)
                .orElseThrow(() -> new RuntimeException("Interview not found"));

        interviewSlot.setInterview(interview);
        interviewSlotRepo.save(interviewSlot);

        interview.getInterviewSlots().add(interviewSlot);
        interviewRepo.save(interview);
    }

    public void createInterview(int hrId, InterviewEntity interview) {

        if (!isAdmin(hrId)) {
            throw new RuntimeException("Only admins can create interviews");
        }

        HREntity hr = hrRepo.findById(hrId)
                .orElseThrow(() -> new RuntimeException("HR not found"));

        interview.setHr(hr);
        hr.getInterview().add(interview);

        hrRepo.save(hr);
    }

    public List<InterviewSlotEntity> getAllInterviewSlots(int hrId) {

        if (!isAdmin(hrId)) {
            throw new RuntimeException("Only admins can get interview slots");
        }

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
                interviewRepo.findById(slot.getInterview().getId().intValue())
                        .ifPresent(interview -> {
                            interview.getInterviewSlots().add(slot);
                            interviewRepo.save(interview);
                        });
            }
        });
    }



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
        if (!hrRepo.findAll().stream().filter(hr -> hr.getId() == hrId).findFirst().map(hr -> hr.getIsAdmin())
                .orElse(false)) {
            throw new RuntimeException("Only admins can get volunteers");
        }
        return hrRepo.findAll().stream().flatMap(hr -> hr.getVolunteers().stream()).toList();
    }

    public void assignVolunteerToHR(int assignerHrId, int volunteerId, int targetHrId) {
        if (!hrRepo.findAll().stream().filter(hr -> hr.getId() == assignerHrId).findFirst().map(hr -> hr.getIsAdmin())
                .orElse(false)) {
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

    public void sendEmailToVolunteer(int volunteerId, int hrId) {
        HREntity hr = hrRepo.findById(hrId)
                .orElseThrow(() -> new RuntimeException("HR not found"));

        VolunteerEntity volunteer = volunteerRepo.findById(volunteerId)
                .orElseThrow(() -> new RuntimeException("Volunteer not found"));

        String toEmail = volunteer.getEmail();
        String fromEmail = "misrshoryanelataa@gmail.com";
        String subject = "Update on Your Volunteer Application";
        String emailContent;
        String emailGenerated;
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

                emailGenerated = generated;

                String passwordGenerated = UUID.randomUUID().toString().substring(0, 8);
                staffRepo.save(new StaffEntity() {
                    {
                        setName(volunteer.getName());
                        setEmail(volunteer.getEmail());
                        setOfficialEmail(emailGenerated);
                        setPassword(passwordGenerated);
                        setRole(assignedDepartment);
                    }
                });
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
        message.setFrom(fromEmail);

        mailSender.send(message);
    }

    public List<VolunteerEntity> getVolunteersAssignedToHR(int hrId) {
        HREntity hr = hrRepo.findById(hrId)
                .orElseThrow(() -> new RuntimeException("HR not found"));
        return hr.getVolunteers();
    }

    public void acceptVolunteer(int volunteerId, int hrId, Role assignedDepartment) {
        HREntity hr = hrRepo.findById(hrId)
                .orElseThrow(() -> new RuntimeException("HR not found"));

        VolunteerEntity volunteer = volunteerRepo.findById(volunteerId)
                .orElseThrow(() -> new RuntimeException("Volunteer not found"));

        if (!hr.getVolunteers().contains(volunteer)) {
            throw new RuntimeException("Volunteer is not assigned to this HR");
        }

        if (!hr.getIsAdmin()) {
            volunteer.setStatus(volunteerStatus.ACCEPTED);
            volunteerRepo.save(volunteer);
            chooseDepartmentForVolunteer(volunteerId, hrId, assignedDepartment);

        }
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

        if (!hr.getVolunteers().contains(volunteer)) {
            throw new RuntimeException("Volunteer is not assigned to this HR");
        }
        if (!hr.getIsAdmin()) {
            volunteer.setStatus(volunteerStatus.REJECTED);
            volunteerRepo.save(volunteer);
            sendEmailToVolunteer(volunteerId, hrId);
            volunteerRepo.delete(volunteer);
        }

    }

}
