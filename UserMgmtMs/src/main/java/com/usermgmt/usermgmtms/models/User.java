package com.usermgmt.usermgmtms.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "Dtype")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserID")
    private long userID;
    @Column(name = "Name")
    private String name;

    @Column(name = "Email", unique = true)
    private String email;
    @JsonIgnore
    @Column(name = "Password")
    private String password;

    @Column(name = "Affiliation")
    private String affiliation;

    @Column(name = "Bio")
    private String bio;

    @Enumerated(EnumType.STRING)
    @Column(name = "UserType")
    private UserType userType;

    public enum UserType {
        Admin,
        Instructor,
        Student
    }
}
