package com.animesh.facecount.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
public class AttendanceRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(length = 20)
    private String userId;
//    private Integer courseId;

    private LocalDate date;

    @Column(length = 10)
    private String status;

    private LocalDateTime timestamp;

}
