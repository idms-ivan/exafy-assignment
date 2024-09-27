package com.exafy.assignment.repository;

import com.exafy.assignment.model.NotificationTriggers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationTriggerRepository extends JpaRepository<NotificationTriggers, Integer> {
}
