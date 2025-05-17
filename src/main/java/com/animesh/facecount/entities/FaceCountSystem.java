package com.animesh.facecount.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "facecount_systems")
public class FaceCountSystem {

    @Id
    private int system_id;

    @Column(nullable = false)
    private String department;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role;

}
