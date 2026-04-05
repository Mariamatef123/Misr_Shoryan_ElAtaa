package com.misrshoryanelataa.misr_shoryan_elataa.Repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.misrshoryanelataa.misr_shoryan_elataa.Models.PREntity;

@Repository
public interface PrRepo extends JpaRepository<PREntity, Integer> {
}