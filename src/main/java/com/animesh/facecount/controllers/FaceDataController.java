package com.animesh.facecount.controllers;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@RestController
//@RequiredArgsConstructor
@RequestMapping("api/encodings")
public class FaceDataController {

    private final Path fileStorageLocation = Paths.get("uploads").toAbsolutePath().normalize();

    @PostConstruct
    public void init() throws IOException {
        Files.createDirectories(fileStorageLocation);
    }

    @PostMapping("upload")
    @PreAuthorize("hasAnyRole('SYSTEM', 'ADMIN', 'FACULTY')")
    public ResponseEntity<String> uploadCSV(@RequestParam("file") MultipartFile file) {
        try {
            String filename = "student_data.csv";
            Path targetLocation = fileStorageLocation.resolve(filename);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return new ResponseEntity<>("File uploaded and saved as: " + filename, HttpStatus.OK);
        } catch (IOException exception) {
            return new ResponseEntity<>("Could Not Store File", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/download")
    @PreAuthorize("hasAnyRole('SYSTEM', 'ADMIN', 'FACULTY')")
    public ResponseEntity<?> downloadEncryptedCSV() {
        try {
            Path filePath = fileStorageLocation.resolve("student_data.csv").normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"student_data.csv\"")
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ByteArrayResource(("Download failed: " + e.getMessage()).getBytes()));
        }
    }

}
