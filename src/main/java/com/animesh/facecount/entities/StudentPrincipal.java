package com.animesh.facecount.entities;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class StudentPrincipal implements UserDetails {

    private final Student student;
    public StudentPrincipal(Student student) {
        this.student = student;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_" + student.getRole()));
    }

    @Override
    public String getPassword() {
        return student.getPassword();
    }

    @Override
    public String getUsername() {
        return String.valueOf(student.getStudent_id());
    }
}