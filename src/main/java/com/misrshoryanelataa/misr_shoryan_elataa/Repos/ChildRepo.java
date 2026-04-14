package com.misrshoryanelataa.misr_shoryan_elataa.Repos;

import com.misrshoryanelataa.misr_shoryan_elataa.Models.ChildEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChildRepo extends JpaRepository <ChildEntity,Integer> {
    List<ChildEntity> findByLepId(int lepId);
}
