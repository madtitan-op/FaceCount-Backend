package com.animesh.facecount.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

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

    /*
    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
    }


    public String getCourseCode() {
        return courseCode;
    }

//    public void setCourseCode(String courseCode) {
//        this.courseCode = courseCode;
//    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Integer getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(Integer facultyId) {
        this.facultyId = facultyId;
    }

    */
}
