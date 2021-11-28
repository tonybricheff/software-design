package com.brichev.mvc.repository;

import com.brichev.mvc.model.enitity.TaskEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TaskRepository extends CrudRepository<TaskEntity, Integer> {
    @Query(nativeQuery = true, value = "SELECT * FROM task t WHERE t.task_list_id = ?1 AND t.task_id = ?2")
    Optional<TaskEntity> findByTaskIdAndTaskLis(Integer listId, Integer taskId);
}
