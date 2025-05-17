package com.animesh.facecount.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Entity class representing an attendance record.
 *
 * @version 1.0
 */
@Data
@Entity
public class AttendanceRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer attendance_id;

    @Column(length = 15)
    private Long userid;

    @Column(length = 10)
    private String status;

    private LocalDate date;
    private LocalTime time;

    @Column(nullable = true)
    private Long marked_by_faculty_id;
    @Column(nullable = true)
    private Integer marked_by_system_id;

}
