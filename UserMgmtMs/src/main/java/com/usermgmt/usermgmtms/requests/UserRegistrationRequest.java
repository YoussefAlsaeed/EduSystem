package com.usermgmt.usermgmtms.requests;
import com.usermgmt.usermgmtms.models.User;
import lombok.Data;


@Data
public class UserRegistrationRequest {

    private String name;

    private String email;

    private String password;

    private String affiliation;

    private String bio;

    private User.UserType userType;

    private int YearsOfExperience;

}
