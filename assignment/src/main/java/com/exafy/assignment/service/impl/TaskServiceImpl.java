package com.exafy.assignment.service.impl;

import com.exafy.assignment.constants.CategoryValues;
import com.exafy.assignment.constants.PriorityValues;
import com.exafy.assignment.constants.StatusValues;
import com.exafy.assignment.converter.CreateTaskDtoToTaskConverter;
import com.exafy.assignment.converter.TaskToTaskDtoConverter;
import com.exafy.assignment.dto.CreateTaskDto;
import com.exafy.assignment.dto.TaskDto;
import com.exafy.assignment.exception.NotFoundException;
import com.exafy.assignment.model.NotificationTriggers;
import com.exafy.assignment.model.Task;
import com.exafy.assignment.repository.NotificationTriggerRepository;
import com.exafy.assignment.repository.TaskRepository;
import com.exafy.assignment.service.EmailService;
import com.exafy.assignment.service.TaskService;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskToTaskDtoConverter toDtoConverter;
    private final CreateTaskDtoToTaskConverter toTaskConverter;
    private final EmailService emailService;
    private final NotificationTriggerRepository notificationTriggerRepository;

    @Override
    public List<TaskDto> getTasks(String status, String sortBy, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        return taskRepository.findAll((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(status != null){
                predicates.add(cb.equal(root.get("status"), status));
            }

                if ("priority".equals(sortBy)) {
                    query.orderBy(cb.asc(root.get("priority")));
                } else if ("dueDate".equals(sortBy)) {
                    query.orderBy(cb.asc(root.get("dueDate")));
                }

            return cb.and(predicates.toArray(new Predicate[0]));
        }, pageable).stream().map(toDtoConverter::convert).collect(Collectors.toList());

    }

    @Override
    public TaskDto getTask(int id) {
        return toDtoConverter.convert(taskRepository.findById(id).orElseThrow(() -> new NotFoundException("Task with id %s not found.")));
    }

    @Override
    public TaskDto createTask(CreateTaskDto task) {

        PriorityValues.checkIsValid(task.getPriority());
        CategoryValues.checkIsValid(task.getCategory());
        StatusValues.checkIsValid(task.getStatus());

        final Task newTask = toTaskConverter.convert(task);
        taskRepository.save(newTask);
   //     emailService.sendEmail(newTask.getAssignedUser(), "Task created", newTask.getTitle());
        return toDtoConverter.convert(newTask);
    }

    @Override
    public TaskDto updateTask(int id, Task task) {
        Task taskToUpdate = taskRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("Task with id %s not found.", id)));

        taskRepository.save(taskToUpdate);
        return toDtoConverter.convert(taskToUpdate);
    }

    @Override
    public void deleteTask(int id) {
        Task taskToDelete = taskRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Task with id %s not found.", id)));
        taskRepository.delete(taskToDelete);
    }

    @Override
    public TaskDto completeTask(int id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Task with id %s not found.", id)));

        task.setStatus(StatusValues.COMPLETED.getStatusValues());

        taskRepository.save(task);

        return toDtoConverter.convert(task);
    }

    @Override
@Scheduled(cron = "*/20 * * * * ?")
    public void checkLowPriorityTasks() {
        final List<Task> tasks = tasksWithNotification(PriorityValues.LOW.getPriorityValue());

        for (Task task : tasks) {
            emailService.sendEmail(task.getAssignedUser(), "Low Priority Task", task.getPriority());
        }
    }

    @Override
@Scheduled(cron = "*/10 * * * * ?")
    public void checkMediumPriorityTasks() {
        final List<Task> tasks = tasksWithNotification(PriorityValues.MEDIUM.getPriorityValue());

        for (Task task : tasks) {
            emailService.sendEmail(task.getAssignedUser(), "Medium Priority Task", task.getPriority());
        }
    }

    @Override
   @Scheduled(cron = "*/5 * * * * ?")
    public void checkHighPriorityTasks() {
        final List<Task> tasks = tasksWithNotification(PriorityValues.HIGH.getPriorityValue());

        for (Task task : tasks) {
            emailService.sendEmail(task.getAssignedUser(), "High Priority Task", task.getPriority());
        }
    }

    private List<Task> tasksWithNotification(String priority){
        List<NotificationTriggers> statuses = notificationTriggerRepository.findAll();

        List<String> checkStatuses = new ArrayList<>();

        for(NotificationTriggers status: statuses){
            checkStatuses.add(status.getStatus());
        }

      return taskRepository.findAllTasksByPriorityAndStatus(priority, checkStatuses);
    }

}