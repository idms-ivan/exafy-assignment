package com.exafy.assignment.repository;

import com.exafy.assignment.model.Task;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TaskRepositoryTest {

    @Mock
    private TaskRepository taskRepository;

    private Task task1, task2;

    @Before
    public void setUp() {

        task1 = Task.builder()
                .title("Task 1")
                .priority("high")
                .status("Pending")
                .dueDate(LocalDate.now().plusDays(2))
                .assignedUser("user1@example.com")
                .build();

        task2 = Task.builder()
                .title("Task 2")
                .priority("high")
                .status("Completed")
                .dueDate(LocalDate.now().plusDays(5))
                .assignedUser("user2@example.com")
                .build();

    }

    @Test
    public void testFindAllTasksByPriorityAndStatus() {

        // Arrange
        String priority = "high";
        List<String> statuses = List.of("Pending", "Completed");
        LocalDate today = LocalDate.now();
        LocalDate nextWeek = LocalDate.now().plusDays(7);

        List<Task> expectedTasks = List.of(task1, task2);


        when(taskRepository.findAllTasksByPriorityAndStatus(priority, statuses, today, nextWeek))
                .thenReturn(expectedTasks);


        List<Task> tasks = taskRepository.findAllTasksByPriorityAndStatus(priority, statuses, today, nextWeek);


        assertEquals(2, tasks.size());
        assertTrue(tasks.contains(task1));
        assertTrue(tasks.contains(task2));
    }
}
