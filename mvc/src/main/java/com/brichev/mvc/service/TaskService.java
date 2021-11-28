package com.brichev.mvc.service;

import com.brichev.mvc.model.dto.Task;
import com.brichev.mvc.model.dto.TaskList;

import java.util.List;

public interface TaskService {
    void completeTask(Integer listId, Integer taskId);

    void addTaskList(TaskList taskList);

    void addTask(Task task);

    void deleteTask(Integer taskListId, Integer taskId);

    void deleteTaskList(Integer taskListId);

    List<TaskList> getAllTaskLists();
}
