package com.notifmgmt.notifmgmtms.services;

import com.notifmgmt.notifmgmtms.repositories.NotificationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @Autowired
    NotificationRepo notificationRepo;
}
