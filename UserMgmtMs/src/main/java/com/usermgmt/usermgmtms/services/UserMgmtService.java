package com.usermgmt.usermgmtms.services;

import com.usermgmt.usermgmtms.models.User;
import com.usermgmt.usermgmtms.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserMgmtService {

    @Autowired
    private UserRepo userRepo;

    public User getUserInfo(User.UserType userType, Long id)
    {
        return userRepo.findByUserTypeAndUserID(userType,id);
    }

    public boolean deleteUser(Long id) {
        // Check if the user exists
        if (!userRepo.existsById(id)) {
            return false;
        }

        // Retrieve the user by ID
        User user = userRepo.getById(id);

        // Check if the user is an admin
        if (user.getUserType() == User.UserType.Admin) {
            return false;
        }

        // Delete the user if not an admin
        userRepo.deleteById(id);
        return true;
    }

    public List<User> getNonAdminUsers() {
        return userRepo.findByUserTypeNot(User.UserType.Admin);
    }

}
