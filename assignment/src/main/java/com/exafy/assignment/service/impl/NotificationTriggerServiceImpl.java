package com.exafy.assignment.service.impl;

import com.exafy.assignment.constants.StatusValues;
import com.exafy.assignment.dto.CreateNotificationTrigger;
import com.exafy.assignment.model.NotificationTriggers;
import com.exafy.assignment.repository.NotificationTriggerRepository;
import com.exafy.assignment.service.NotificationTriggerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationTriggerServiceImpl implements NotificationTriggerService {

    private final NotificationTriggerRepository notificationTriggerRepository;

    @Override
    public void addTrigger(CreateNotificationTrigger trigger) {

        StatusValues.checkIsValid(trigger.getStatus());

        NotificationTriggers notificationTrigger = new NotificationTriggers();
        notificationTrigger.setStatus(trigger.getStatus());
        notificationTriggerRepository.save(notificationTrigger);
    }
}
