package com.animesh.facecount.services;

import com.animesh.facecount.entities.*;
import com.animesh.facecount.repositories.FacultyRepository;
import com.animesh.facecount.repositories.StudentRepository;
import com.animesh.facecount.repositories.SystemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    private final StudentRepository studentRepository;
    private final FacultyRepository facultyRepository;
    private final SystemRepository systemRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails userDetails = loadStudentByUsername(username);
        if (userDetails != null) {
            return userDetails;
        }

        userDetails = loadFacultyByUsername(username);
        if (userDetails != null) {
            return userDetails;
        }

        userDetails = loadFCSystemByUsername(username);
        if (userDetails != null) {
            return userDetails;
        }

        throw new UsernameNotFoundException("404! USER NOT FOUND");
    }

    private UserDetails loadStudentByUsername(String username) {
        Student student = studentRepository.findById(Long.valueOf(username)).orElse(null);
        if (student != null) {
            return new StudentPrincipal(student);
        }
        return null;
    }

    private UserDetails loadFacultyByUsername(String username) {
        Faculty faculty = facultyRepository.findById(Long.valueOf(username)).orElse(null);
        if (faculty != null) {
            return new FacultyPrincipal(faculty);
        }
        return null;
    }

    private UserDetails loadFCSystemByUsername(String username) {
        FaceCountSystem fcSystem = systemRepository.findById(Integer.valueOf(username)).orElse(null);
        if (fcSystem != null) {
            return new FaceCountSystemPrincipal(fcSystem);
        }
        return null;
    }

}
