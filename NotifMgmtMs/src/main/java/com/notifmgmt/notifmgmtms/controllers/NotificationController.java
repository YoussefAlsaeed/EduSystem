package com.notifmgmt.notifmgmtms.controllers;

import com.notifmgmt.notifmgmtms.models.Notification;
import com.notifmgmt.notifmgmtms.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class NotificationController {
    @Autowired
    NotificationService notificationService;

    @PostMapping("/send")
    public ResponseEntity<String> sendMessage(@RequestBody Notification notification) {
        notificationService.createNotification(notification.getMessage(),notification.getStudentId(),notification.getInstructorId());
        return ResponseEntity.ok("Message sent successfully");
    }

    @GetMapping("/getNotifications/{studentId}")
    public ResponseEntity<List<Notification>> getNotifications(@PathVariable Long studentId) {
        List<Notification> notifications = notificationService.getNotificationsByStudentId(studentId);
        return ResponseEntity.ok(notifications);
    }
}
