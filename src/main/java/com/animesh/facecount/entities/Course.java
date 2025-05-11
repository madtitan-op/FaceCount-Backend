package com.animesh.facecount.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a course entity.
 * 
 * @author Animesh Mahata
 * @version 1.0
 */
@Getter
@Entity
public class Course {

    @Id
    @GeneratedValue
    private Integer id;

    @Setter
    @Column(length = 10, unique = true)
    private String courseCode;

    @Setter
    @Column(length = 50)
    private String courseName;

    @Setter
    private Integer facultyId;

    public Course(String courseCode, String courseName, Integer facultyId) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.facultyId = facultyId;
    }

    public Course() {}
}
