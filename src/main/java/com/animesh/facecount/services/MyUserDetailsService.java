package com.animesh.facecount.services;

import com.animesh.facecount.entities.User;
import com.animesh.facecount.entities.UserPrincipal;
import com.animesh.facecount.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepo.findByUserid(username);
        if (user == null) {
            throw new UsernameNotFoundException("404! USER NOT FOUND");
        }
        return new UserPrincipal(user);
    }
}
