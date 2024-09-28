package com.exafy.assignment.repository;

import com.exafy.assignment.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer>, JpaSpecificationExecutor<Task> {


    @Query(value = "SELECT * FROM task WHERE task.priority = :priority AND task.status IN :statuses AND task.due_date BETWEEN :today AND :nextWeek", nativeQuery = true)
    List<Task> findAllTasksByPriorityAndStatus(@Param("priority") String priority, @Param("statuses") List<String> statuses, @Param("today") LocalDate today, @Param("nextWeek") LocalDate nextWeek);

}
