package com.exafy.assignment.service;

import com.exafy.assignment.dto.CreateTaskDto;
import com.exafy.assignment.dto.TaskDto;


import java.util.List;

public interface TaskService {
    List<TaskDto> getTasks(String status, String sortBy, int page, int size);
    TaskDto createTask(CreateTaskDto task);
    TaskDto updateTask(int id, TaskDto taskDto);
    void deleteTask(int id);
    TaskDto completeTask(int id);
}
