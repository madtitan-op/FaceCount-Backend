package com.animesh.facecount.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class AttendanceRecord {

    @Id
    @GeneratedValue
    private Integer id;

    private Integer userId;
    private Integer courseId;
    private LocalDate date;
    private String status;
    private LocalDateTime timestamp;

}
