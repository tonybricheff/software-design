package com.brichev.mvc.repository;

import com.brichev.mvc.model.enitity.TaskListEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TaskListRepository extends CrudRepository<TaskListEntity, Integer> {
    @Query(nativeQuery = true, value = "SELECT * FROM task_list tl WHERE tl.task_list_id = ?1")
    Optional<TaskListEntity> findById(Integer id);
}
