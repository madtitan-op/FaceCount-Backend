package com.animesh.facecount.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a user entity.
 * 
 * @author Animesh Mahata
 * @version 1.0
 */
@Entity
@Table(name = "users")
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Setter
    @Column(unique = true, nullable = false)
    private String userid;    //ROLL NO

    @Setter
    @Column(length = 50, nullable = false)
    private String name;

    @Setter
    @Column(nullable = false)
    private short yop;    //YEAR OF PASSING

    @Setter
    @Column(nullable = false)
    private String department;

    @Setter
    @Column(length = 80, nullable = false)
    private String email;

    @Setter
    @Column(length = 254, nullable = false)
    private String password;

    @Setter
    @Column(length = 25, nullable = false)
    private String role;

    public User(String userid, String name, short yop, String department, String email, String password, String role) {
        this.userid = userid;
        this.name = name;
        this.yop = yop;
        this.department = department;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public User() {}
}

