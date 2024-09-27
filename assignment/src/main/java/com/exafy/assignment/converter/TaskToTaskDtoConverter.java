package com.exafy.assignment.converter;

import com.exafy.assignment.dto.TaskDto;
import com.exafy.assignment.model.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TaskToTaskDtoConverter implements Converter<Task, TaskDto> {
    @Override
    public TaskDto convert(Task source) {
        return TaskDto.builder()
                .id(source.getId())
                .title(source.getTitle())
                .description(source.getDescription())
                .dueDate(source.getDueDate())
                .priority(source.getPriority())
                .status(source.getStatus())
                .category(source.getCategory())
                .assignedUser(source.getAssignedUser())
                .build();
    }
}
