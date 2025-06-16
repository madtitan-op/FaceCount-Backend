package com.animesh.facecount.config;

import com.animesh.facecount.dto.faculty.FacultyRequestDTO;
import com.animesh.facecount.services.FacultyService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class AdminSeeder {

    private final FacultyService service;

    @Bean
    public CommandLineRunner seedAdmin() {
        return args -> {

            FacultyRequestDTO admin = new FacultyRequestDTO(
                    100L,
                    "admin@facecount.com",
                    "Admin",
                    "",
                    "ADMIN",
                    "Dept"
            );

            if (service.getFacultyById(100L) == null) {
                service.addFaculty(admin);
            }
        };
    }
}

