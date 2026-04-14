package com.misrshoryanelataa.misr_shoryan_elataa.Services;

import com.misrshoryanelataa.misr_shoryan_elataa.Enums.BloodType;
import com.misrshoryanelataa.misr_shoryan_elataa.Enums.DonationType;
import com.misrshoryanelataa.misr_shoryan_elataa.Enums.volunteerStatus;
import com.misrshoryanelataa.misr_shoryan_elataa.Models.DonorEntity;
import com.misrshoryanelataa.misr_shoryan_elataa.Repos.DonorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DonorService {
    @Autowired
    private DonorRepo donorRepo;

    public Object createDonor(DonorEntity donor) {
        return donorRepo.save(donor);
    }
    public Object getDonorById(int id) {
        try {
            return donorRepo.findById(id).orElse(null);
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public List<DonorEntity> getAvailableDropDownDonors(BloodType bloodType) {
        return donorRepo.findByBloodTypeAndDonationTypeAndDonorStatusAndGroupIsNull(bloodType, DonationType.REPEAT, volunteerStatus.ACCEPTED);
    }

    public List<DonorEntity> getAcceptedRepeatDonors() {
        return donorRepo.findByDonationTypeAndDonorStatus(DonationType.REPEAT, volunteerStatus.ACCEPTED);
    }

    public List<DonorEntity> getAcceptedOneTimeDonors() {
        return donorRepo.findByDonationTypeAndDonorStatus(DonationType.ONE_TIME, volunteerStatus.ACCEPTED);
    }

    public List<DonorEntity> getPendingDonors() {
        return donorRepo.findByDonorStatus(volunteerStatus.PENDING);
    }

    public Object updateDonor(int id, DonorEntity donor) {
        try {
            if (donorRepo.existsById(id)) {
                donor.setId(id);
                return donorRepo.save(donor);
            } else {
                return "Donor not found";
            }
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public Object deleteDonor(int id) {
        try {
            if (donorRepo.existsById(id)) {
                donorRepo.deleteById(id);
                return "Donor deleted successfully";
            } else {
                return "Donor not found";
            }
        } catch (Exception e) {
            return e.getMessage();
        }
    }
    public Object getDonorTypes() {
       return List.of(DonationType.values());
    }
    public Object getBloodTypes() {
        return List.of(BloodType.values());
    }

    
}
