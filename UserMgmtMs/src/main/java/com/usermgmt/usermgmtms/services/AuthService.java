package com.usermgmt.usermgmtms.services;

import com.usermgmt.usermgmtms.models.Admin;
import com.usermgmt.usermgmtms.models.Instructor;
import com.usermgmt.usermgmtms.models.Student;
import com.usermgmt.usermgmtms.models.User;
import com.usermgmt.usermgmtms.repositories.UserRepo;
import com.usermgmt.usermgmtms.requests.UserRegistrationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AuthService {

    @Autowired
    private UserRepo userRepo;

    public boolean registerUser(UserRegistrationRequest userRequest) {
        try {
            String email = userRequest.getEmail();

            // Check if the user already exists
            if (userRepo.existsByEmail(email)) {
                System.out.println("User already exists!");
                return false;
            }

            User persistUser;

            switch (userRequest.getUserType()) {
                case Admin:
                    persistUser = new Admin();
                    break;
                case Instructor:
                    persistUser = new Instructor();
                    ((Instructor) persistUser).setYearsOfExperience(userRequest.getYearsOfExperience());
                    break;
                case Student:
                    persistUser = new Student();
                    break;
                default:
                    System.out.println("Unsupported user type!");
                    return false;
            }

            persistUser.setName(userRequest.getName());
            persistUser.setEmail(email);
            persistUser.setAffiliation(userRequest.getAffiliation());
            persistUser.setPassword(userRequest.getPassword());
            persistUser.setBio(userRequest.getBio());
            persistUser.setUserType(userRequest.getUserType());

            userRepo.save(persistUser);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean loginUser(String email, String password) {
        try {
            return userRepo.existsByEmailAndPassword(email, password);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public User getUserByEmail(String email) {
        return userRepo.findByEmail(email);
    }
}
