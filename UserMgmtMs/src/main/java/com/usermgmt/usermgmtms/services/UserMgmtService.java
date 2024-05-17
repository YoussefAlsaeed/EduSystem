package com.usermgmt.usermgmtms.services;

import com.usermgmt.usermgmtms.models.User;
import com.usermgmt.usermgmtms.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class UserMgmtService {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private RestTemplate restTemplate;

    public User getUserInfo(User.UserType userType, Long id)
    {
        return userRepo.findByUserTypeAndUserID(userType,id);
    }

    @Value("${courseMgmtService.baseURL}")
    private String courseMgmtBaseURL;

    public boolean deleteUser(Long id) {
        // Check if the user exists
        if (!userRepo.existsById(id)) {
            return false;
        }

        User user = userRepo.findById(id).orElse(null);

        if (user == null) {
            return false;
        }

        // Check if the user is an admin
        if (user.getUserType() == User.UserType.Admin) {
            return false;
        }

        if (user.getUserType() == User.UserType.Instructor) {
            String url = courseMgmtBaseURL + "/course/instructor/" + id;
            restTemplate.delete(url);
        }

        // Delete the user
        userRepo.deleteById(id);
        return true;
    }

    public List<User> getNonAdminUsers() {
        return userRepo.findByUserTypeNot(User.UserType.Admin);
    }

}
