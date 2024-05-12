package com.usermgmt.usermgmtms.controllers;

import com.usermgmt.usermgmtms.models.User;
import com.usermgmt.usermgmtms.services.UserMgmtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserMgmtController {

    @Autowired
    private UserMgmtService userMgmtService;

    @GetMapping("/{type}/{id}")
    public ResponseEntity<String> getUserInfo(@PathVariable("type") User.UserType type, @PathVariable("id") Long id) {
        User user = userMgmtService.getUserInfo(type, id);
        if (user != null) {
            return ResponseEntity.ok("Exists");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        boolean deleted = userMgmtService.deleteUser(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<User>> getNonAdminUsers() {
        List<User> users = userMgmtService.getNonAdminUsers();
        if (!users.isEmpty()) {
            return ResponseEntity.ok(users);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
