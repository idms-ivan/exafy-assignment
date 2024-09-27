package com.exafy.assignment.service;

import com.exafy.assignment.dto.CreateTaskDto;
import com.exafy.assignment.dto.TaskDto;
import com.exafy.assignment.model.Task;

import java.util.List;

public interface TaskService {
    List<TaskDto> getTasks(String status, String sortBy, int page, int size);
    TaskDto getTask(int id);
    TaskDto createTask(CreateTaskDto task);
    TaskDto updateTask(int id, Task task);
    void deleteTask(int id);
    TaskDto completeTask(int id);
    void checkLowPriorityTasks();
    void checkMediumPriorityTasks();
    void checkHighPriorityTasks();
}
