package com.animesh.facecount.entities;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class FacultyPrincipal implements UserDetails {

    private final Faculty faculty;
    public FacultyPrincipal(Faculty faculty) {
        this.faculty = faculty;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_" + faculty.getRole()));
    }

    @Override
    public String getPassword() {
        return faculty.getPassword();
    }

    @Override
    public String getUsername() {
        return String.valueOf(faculty.getFaculty_id());
    }

    public String getRole() {
        return faculty.getRole();
    }
}