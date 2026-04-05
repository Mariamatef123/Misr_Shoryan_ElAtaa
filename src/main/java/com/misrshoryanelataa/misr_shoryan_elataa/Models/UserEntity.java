package com.misrshoryanelataa.misr_shoryan_elataa.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.validation.constraints.Email;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class UserEntity {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
int id;
String name;
@Email
String email;
String password;
public void setEmail(String email) {
    this.email = email;
}
public void setId(int id) {
    this.id = id;
}
public void setName(String name) {
    this.name = name;
}
public void setPassword(String password) {
    this.password = password;
}
public String getEmail() {
    return email;
}
public int getId() {
    return id;
}
public String getName() {
    return name;
}
public String getPassword() {
    return password;
}
}