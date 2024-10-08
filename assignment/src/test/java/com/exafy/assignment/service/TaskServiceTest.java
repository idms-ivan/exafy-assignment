package com.exafy.assignment.service;

import com.exafy.assignment.constants.CategoryValues;
import com.exafy.assignment.constants.PriorityValues;
import com.exafy.assignment.constants.StatusValues;
import com.exafy.assignment.converter.CreateTaskDtoToTaskConverter;
import com.exafy.assignment.converter.TaskToTaskDtoConverter;
import com.exafy.assignment.dto.CreateTaskDto;
import com.exafy.assignment.dto.TaskDto;
import com.exafy.assignment.model.Task;
import com.exafy.assignment.repository.NotificationTriggerRepository;
import com.exafy.assignment.repository.TaskRepository;
import com.exafy.assignment.service.impl.TaskServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskToTaskDtoConverter toDtoConverter;

    @Mock
    private CreateTaskDtoToTaskConverter toTaskConverter;

    @Mock
    private EmailService emailService;

    @Mock
    private NotificationTriggerRepository notificationTriggerRepository;

    private TaskServiceImpl taskService;

    @Before
    public void setUp() throws Exception {
        this.taskService = new TaskServiceImpl(taskRepository, toDtoConverter, toTaskConverter, emailService, notificationTriggerRepository);
    }

    @Test
    public void shouldGetAllTasks() {

        final Task task1 = Task.builder()
                .id(1)
                .title("Zadatak 1")
                .description("Opis 1")
                .dueDate(LocalDate.now())
                .priority("low")
                .status("Completed")
                .category("work")
                .assignedUser("mgdasezna@gmail.com")
                .build();

        final Task task2 = Task.builder()
                .id(2)
                .title("Zadatak 2")
                .description("Opis 2")
                .dueDate(LocalDate.now())
                .priority("medium")
                .status("Pending")
                .category("personal")
                .assignedUser("user@example.com")
                .build();

        List<Task> tasks = List.of(task1, task2);
        Page<Task> taskPage = new PageImpl<>(tasks, PageRequest.of(0, 10), tasks.size());


        when(taskRepository.findAll(Mockito.<Specification<Task>>any(), Mockito.<Pageable>any())).thenReturn(taskPage);


        when(toDtoConverter.convert(task1)).thenReturn(new TaskDto(task1.getId(), task1.getTitle(), task1.getDescription(), task1.getDueDate(), task1.getPriority(), task1.getStatus(), task1.getCategory(), task1.getAssignedUser()));
        when(toDtoConverter.convert(task2)).thenReturn(new TaskDto(task2.getId(), task2.getTitle(), task2.getDescription(), task2.getDueDate(), task2.getPriority(), task2.getStatus(), task2.getCategory(), task2.getAssignedUser()));


        final List<TaskDto> response = taskService.getTasks(null, null, 0, 10);


        assertEquals(2, response.size()); // Check the number of tasks returned
        assertEquals("Zadatak 1", response.get(0).getTitle()); // Check the title of the first task
        assertEquals("Zadatak 2", response.get(1).getTitle()); // Check the title of the second task
    }

    @Test
    public void shouldCreateTask() {


        final CreateTaskDto createTaskDto = CreateTaskDto.builder()
                .title("New Task")
                .description("New Task Description")
                .dueDate(LocalDate.now())
                .priority("high")
                .status("Pending")
                .category("work")
                .assignedUser("test@example.com")
                .build();


        final Task newTask = Task.builder()
                .id(1)
                .title(createTaskDto.getTitle())
                .description(createTaskDto.getDescription())
                .dueDate(createTaskDto.getDueDate())
                .priority(createTaskDto.getPriority())
                .status(createTaskDto.getStatus())
                .category(createTaskDto.getCategory())
                .assignedUser(createTaskDto.getAssignedUser())
                .build();


        final TaskDto taskDto = new TaskDto(
                newTask.getId(),
                newTask.getTitle(),
                newTask.getDescription(),
                newTask.getDueDate(),
                newTask.getPriority(),
                newTask.getStatus(),
                newTask.getCategory(),
                newTask.getAssignedUser()
        );


        Mockito.mockStatic(PriorityValues.class);
        Mockito.mockStatic(CategoryValues.class);
        Mockito.mockStatic(StatusValues.class);


        PriorityValues.checkIsValid(createTaskDto.getPriority());
        CategoryValues.checkIsValid(createTaskDto.getCategory());
        StatusValues.checkIsValid(createTaskDto.getStatus());


        when(toTaskConverter.convert(createTaskDto)).thenReturn(newTask);
        when(taskRepository.save(newTask)).thenReturn(newTask);


        when(toDtoConverter.convert(newTask)).thenReturn(taskDto);

        TaskDto result = taskService.createTask(createTaskDto);


        assertEquals("New Task", result.getTitle());
        assertEquals("New Task Description", result.getDescription());
        assertEquals("high", result.getPriority());
        assertEquals("Pending", result.getStatus());
        assertEquals("work", result.getCategory());
        assertEquals("test@example.com", result.getAssignedUser());


        Mockito.verify(toTaskConverter).convert(createTaskDto);
        Mockito.verify(taskRepository).save(newTask);
        Mockito.verify(toDtoConverter).convert(newTask);
    }

}
