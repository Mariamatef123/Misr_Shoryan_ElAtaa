package com.misrshoryanelataa.misr_shoryan_elataa.Repos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.misrshoryanelataa.misr_shoryan_elataa.Models.VolunteerEntity;

@Repository
public interface VolunteerRepo extends JpaRepository<VolunteerEntity, Integer> {
      boolean existsByEmail(String email);

      boolean existsByUniversityEmail(String universityEmail);
}