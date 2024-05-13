package com.notifmgmt.notifmgmtms.services;

import com.notifmgmt.notifmgmtms.models.Notification;
import com.notifmgmt.notifmgmtms.repositories.NotificationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    @Autowired
    NotificationRepo notificationRepo;

    public Notification createNotification(String message, Long studentId, Long instructorId) {
        Notification notification = new Notification();
        notification.setMessage(message);
        notification.setStudentId(studentId);
        notification.setInstructorId(instructorId);

        return notificationRepo.save(notification);
    }

    public List<Notification> getNotificationsByStudentId(Long studentId) {
        return notificationRepo.findByStudentId(studentId);
    }

}
