package com.notifmgmt.notifmgmtms.repositories;

import com.notifmgmt.notifmgmtms.models.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepo extends JpaRepository<Notification,Long> {
    List<Notification> findByStudentId(Long studentId);
}
