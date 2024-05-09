package com.usermgmt.usermgmtms.models;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("Student")

public class Student extends User{
}

