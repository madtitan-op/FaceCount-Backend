package com.animesh.facecount.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
public class AttendanceRecord {

    @Id
    @GeneratedValue
    private Integer id;

    private long userId;
    private Integer courseId;
    private LocalDate date;
    private String status;
    private LocalDateTime timestamp;

}
