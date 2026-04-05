package com.misrshoryanelataa.misr_shoryan_elataa.Models;

import com.misrshoryanelataa.misr_shoryan_elataa.Enums.Role;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Entity
public class StaffEntity extends UserEntity {
@Enumerated(EnumType.STRING)
Role role;
public void setRole(Role role) {
    this.role = role;
}
public Role getRole() {
    return role;
}
}