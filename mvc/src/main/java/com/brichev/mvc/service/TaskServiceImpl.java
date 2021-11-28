package com.brichev.mvc.service;

import com.brichev.mvc.model.dto.Task;
import com.brichev.mvc.model.dto.TaskList;
import com.brichev.mvc.model.enitity.TaskEntity;
import com.brichev.mvc.model.enitity.TaskListEntity;
import com.brichev.mvc.repository.TaskListRepository;
import com.brichev.mvc.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.StreamSupport.stream;

@Service
class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final TaskListRepository taskListRepository;

    @Autowired
    TaskServiceImpl(TaskRepository taskRepository, TaskListRepository taskListRepository) {
        this.taskRepository = taskRepository;
        this.taskListRepository = taskListRepository;
    }

    @Override
    public void deleteTask(Integer taskListId, Integer taskId) {
        var taskOptional = taskRepository.findByTaskIdAndTaskLis(taskListId, taskId);
        taskOptional.ifPresent(taskRepository::delete);
    }

    @Override
    public void deleteTaskList(Integer taskListId) {
        var taskListOptional = taskListRepository.findById(taskListId);
        taskListOptional.ifPresent(taskListRepository::delete);
    }

    @Override
    @Transactional
    public void completeTask(Integer taskListId, Integer taskId) {
        var taskOptional = taskRepository.findByTaskIdAndTaskLis(taskListId, taskId);
        taskOptional.ifPresent(taskEntity -> taskEntity.setIsCompleted(true));
    }

    @Override
    @Transactional
    public void addTaskList(TaskList taskList) {
        taskListRepository.save(createFromDto(taskList));
    }

    private TaskListEntity createFromDto(TaskList taskList) {
        return TaskListEntity.builder()
                .description(taskList.getDescription())
                .tasks(new HashSet<>())
                .build();
    }

    @Override
    @Transactional
    public void addTask(Task task) {
        var taskListOptional = taskListRepository.findById(task.getListId());
        taskListOptional.ifPresent(taskListEntity -> {
            var savedTask = taskRepository.save(createFromDto(task, taskListEntity));
            taskListEntity.getTasks().add(savedTask);
        });
    }

    private TaskEntity createFromDto(Task task, TaskListEntity taskListEntity) {
        return TaskEntity.builder()
                .description(task.getDescription())
                .isCompleted(false)
                .taskList(taskListEntity)
                .build();
    }

    @Override
    public List<TaskList> getAllTaskLists() {
        return stream(taskListRepository.findAll().spliterator(), false)
                .map(this::createFromEntity)
                .collect(Collectors.toList());
    }

    private TaskList createFromEntity(TaskListEntity taskListEntity) {
        return TaskList.builder()
                .id(taskListEntity.getTaskListId())
                .description(taskListEntity.getDescription())
                .tasks(
                        taskListEntity.getTasks().stream()
                                .map(this::createFromEntity)
                                .collect(Collectors.toSet())
                )
                .build();
    }

    private Task createFromEntity(TaskEntity taskEntity) {
        return Task.builder()
                .id(taskEntity.getTaskId())
                .description(taskEntity.getDescription())
                .isCompleted(taskEntity.getIsCompleted())
                .listId(taskEntity.getTaskList().getTaskListId())
                .build();
    }
}
