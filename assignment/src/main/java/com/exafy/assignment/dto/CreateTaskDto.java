package com.exafy.assignment.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateTaskDto {

    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    @NotNull(message = "Due date is required")
    private LocalDate dueDate;
    @NotBlank(message = "Priority is required")
    private String priority;
    @NotBlank(message = "Status is required")
    private String status;
    @NotBlank(message = "Category is required")
    private String category;

    @Email(message = "Assigned user must be a valid email address")
    @NotBlank(message = "Assigned user is required")
    private String assignedUser;
}
