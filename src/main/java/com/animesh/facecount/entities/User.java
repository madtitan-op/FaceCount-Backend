package com.animesh.facecount.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
public class User {

    @Id
    @GeneratedValue
    private Integer id;

    @Setter
    @Column(unique = true)
    private String userid;    //ROLL NO

    @Setter
    @Column(length = 50)
    private String name;

    @Setter
    private short yop;    //YEAR OF PASSING

    @Setter
    private String department;

    @Setter
    @Column(length = 80)
    private String email;

    @Setter
    @Column(length = 254)
    private String password;

    @Setter
    @Column(length = 25)
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
/*

    public Integer getUserid() {
        return id;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

 */
}
