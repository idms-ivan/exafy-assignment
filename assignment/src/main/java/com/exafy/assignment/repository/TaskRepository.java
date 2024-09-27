package com.exafy.assignment.repository;

import com.exafy.assignment.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer>, JpaSpecificationExecutor<Task> {


    @Query(value = "SELECT * FROM task WHERE task.priority = :priority AND task.status IN :statuses ", nativeQuery = true)
    List<Task> findAllTasksByPriorityAndStatus(@Param("priority") String priority, @Param("statuses") List<String> statuses);

//    @Query("SELECT t FROM Task t WHERE t.status = :status ORDER BY "
//            + "CASE WHEN :sortBy = 'priority' THEN t.priority END, "
//            + "CASE WHEN :sortBy = 'dueDate' THEN t.dueDate END")
//    List<Task> findAllTasksFilterAndSort(@Param("status") String status, @Param("sortBy") String sortBy);
}
