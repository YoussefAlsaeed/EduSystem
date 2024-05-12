package com.notifmgmt.notifmgmtms.controllers;

import com.notifmgmt.notifmgmtms.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotificationController {
    @Autowired
    NotificationService notificationService;
}
