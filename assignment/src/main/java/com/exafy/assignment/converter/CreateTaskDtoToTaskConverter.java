package com.exafy.assignment.converter;

import com.exafy.assignment.dto.CreateTaskDto;
import com.exafy.assignment.model.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateTaskDtoToTaskConverter implements Converter<CreateTaskDto, Task> {
    @Override
    public Task convert(CreateTaskDto source) {
        return Task.builder()
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
