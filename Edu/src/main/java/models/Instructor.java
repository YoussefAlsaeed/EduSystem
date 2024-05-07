package models;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Instructor")
public class Instructor extends User{
    @Column(name = "YearsOfExperience")
    private int YearsOfExperience;

    public int getYearsOfExperience() {
        return YearsOfExperience;
    }

    public void setYearsOfExperience(int yearsOfExperience) {
        YearsOfExperience = yearsOfExperience;
    }
}
