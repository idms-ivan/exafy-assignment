package com.exafy.assignment.controller;

import com.exafy.assignment.dto.CreateNotificationTrigger;
import com.exafy.assignment.service.NotificationTriggerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/config")
public class ConfigController {


    private final NotificationTriggerService notificationTriggerService;

    @PostMapping("/status-notifications")
    public ResponseEntity<String> configStatusNotification(@RequestBody CreateNotificationTrigger trigger){
        notificationTriggerService.addTrigger(trigger);
        return  ResponseEntity.status(HttpStatus.CREATED).body("Added new trigger for status: " + trigger.getStatus());
    }
}
