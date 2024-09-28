package com.exafy.assignment.controller;

import com.exafy.assignment.dto.ApiResponse;
import com.exafy.assignment.dto.CreateTaskDto;
import com.exafy.assignment.dto.TaskDto;
import com.exafy.assignment.model.Task;
import com.exafy.assignment.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    @GetMapping
    public ResponseEntity<List<TaskDto>> getAllTasks(@RequestParam(value = "status", required = false) String status,
                                                     @RequestParam(value = "sortBy", required = false) String sortBy,
                                                     @RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "10") int size){
        return ResponseEntity.ok(taskService.getTasks(status, sortBy, page, size));
    }

    @PostMapping
    public ResponseEntity<TaskDto> createTask(@RequestBody CreateTaskDto task) {
        final TaskDto createdTask = taskService.createTask(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskDto> updateTask(@PathVariable  int id,@RequestBody TaskDto taskDto) {
        final TaskDto updatedTask = taskService.updateTask(id, taskDto);
        return ResponseEntity.ok(updatedTask);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable int id) {
        taskService.deleteTask(id);
        return ResponseEntity.ok(String.format("Successfully deleted task with id %s", id));
    }

    @PatchMapping("/{id}/complete")
    public ResponseEntity<ApiResponse> markAsCompleted(@PathVariable  int id) {
        System.out.println("************");
        TaskDto completedTask = taskService.completeTask(id);
        ApiResponse response = new ApiResponse(completedTask);
        System.out.println("************");
        System.out.println(response);
        System.out.println("************");
        return ResponseEntity.ok(response);
    }

}
