package com.usermgmt.usermgmtms.models;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity
@DiscriminatorValue("Instructor")
public class Instructor extends User{
    @Column(name = "YearsOfExperience")
    private int YearsOfExperience;

}
