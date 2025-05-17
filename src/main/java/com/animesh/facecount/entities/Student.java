package com.animesh.facecount.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * Represents a Student entity.
 *
 * @author Animesh Mahata
 * @version 1.0
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "students")
public class Student {

    @Id
    private long student_id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role;

    @Column(nullable = false, length = 50)
    private String department;

    @Column(nullable = false)
    private short yop;

}
