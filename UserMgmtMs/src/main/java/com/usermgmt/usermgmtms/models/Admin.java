package com.usermgmt.usermgmtms.models;

import jakarta.persistence.Entity;
import jakarta.persistence.DiscriminatorValue;

@Entity
@DiscriminatorValue("Admin")
public class Admin extends User {
}
