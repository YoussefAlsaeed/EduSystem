package com.usermgmt.usermgmtms.util;

import com.usermgmt.usermgmtms.models.User;
import com.usermgmt.usermgmtms.requests.UserRegistrationRequest;
import com.usermgmt.usermgmtms.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class StartUp implements ApplicationRunner {

    @Autowired
    private AuthService authService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        UserRegistrationRequest adminUser = new UserRegistrationRequest();
        adminUser.setName("admin");
        adminUser.setEmail("admin@admin.com");
        adminUser.setPassword("admin");
        adminUser.setUserType(User.UserType.Admin);
        authService.registerUser(adminUser);
    }
}
