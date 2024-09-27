package com.exafy.assignment.dto;

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

    private String title;
    private String description;
    private LocalDate dueDate;
    private String priority;
    private String status;
    private String category;

    private String assignedUser;
}