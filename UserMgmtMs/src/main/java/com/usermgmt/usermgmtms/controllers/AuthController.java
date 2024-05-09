package com.usermgmt.usermgmtms.controllers;

import com.usermgmt.usermgmtms.models.User;
import com.usermgmt.usermgmtms.requests.UserRegistrationRequest;
import com.usermgmt.usermgmtms.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRegistrationRequest user) {
        try {
            if (authService.registerUser(user)) {
                return ResponseEntity.status(HttpStatus.CREATED).body(user);
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("{\"message\": \"User already exists!\"}");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"message\": \"An error occurred during registration.\"}");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestParam("email") String email, @RequestParam("password") String password) {
        try {
            if (authService.loginUser(email, password)) {
                User user = authService.getUserByEmail(email);
                return ResponseEntity.status(HttpStatus.OK).body(user);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"message\": \"Invalid email or password!\"}");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"message\": \"An error occurred during login.\"}");
        }
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello, World!";
    }
}
