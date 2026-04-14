package com.misrshoryanelataa.misr_shoryan_elataa.Repos;

import com.misrshoryanelataa.misr_shoryan_elataa.Enums.BloodType;
import com.misrshoryanelataa.misr_shoryan_elataa.Enums.DonationType;
import com.misrshoryanelataa.misr_shoryan_elataa.Enums.volunteerStatus;
import com.misrshoryanelataa.misr_shoryan_elataa.Models.DonorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DonorRepo extends JpaRepository<DonorEntity, Integer> {
    List<DonorEntity> findByBloodTypeAndDonationTypeAndDonorStatusAndGroupIsNull(
            BloodType bloodType,
            DonationType state,
            volunteerStatus accepted);
    List<DonorEntity> findByDonationTypeAndDonorStatus(
            DonationType state,
            volunteerStatus accepted);
    List<DonorEntity> findByDonorStatus(
            volunteerStatus pending);

}

