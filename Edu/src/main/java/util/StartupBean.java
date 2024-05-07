package util;

import models.User;
import requests.UserRegisterationRequest;
import services.AuthService;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

@Singleton
@Startup
public class StartupBean {
    @Inject
    private AuthService authService;

    @PostConstruct
    public void init() {
        UserRegisterationRequest adminUser = new UserRegisterationRequest();
        adminUser.setName("admin");
        adminUser.setEmail("admin@admin.com");
        adminUser.setPassword("admin");
        adminUser.setUserType(User.UserType.Admin);

        authService.registerUser(adminUser);
    }
}
