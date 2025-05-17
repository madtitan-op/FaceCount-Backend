package com.animesh.facecount.entities;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class FaceCountSystemPrincipal implements UserDetails {

    private final FaceCountSystem fcs;
    public FaceCountSystemPrincipal(FaceCountSystem fcs) {
        this.fcs = fcs;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_" + fcs.getRole()));
    }

    @Override
    public String getPassword() {
        return fcs.getPassword();
    }

    @Override
    public String getUsername() {
        return String.valueOf(fcs.getSystem_id());
    }
}
