package com.brichev.mvc.controller;

import com.brichev.mvc.model.dto.Task;
import com.brichev.mvc.model.dto.TaskList;
import com.brichev.mvc.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class TaskController {
    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/done/{taskListId}/{taskId}")
    public String completeTask(@PathVariable("taskListId") Integer taskListId, @PathVariable("taskId") Integer taskId) {
        taskService.completeTask(taskListId, taskId);
        return "redirect:/get-lists";
    }

    @GetMapping("/delete/{taskListId}/{taskId}")
    public String deleteTask(@PathVariable("taskListId") Integer taskListId, @PathVariable("taskId") Integer taskId) {
        taskService.deleteTask(taskListId, taskId);
        return "redirect:/get-lists";
    }

    @GetMapping("/delete/{taskListId}")
    public String deleteTaskList(@PathVariable("taskListId") Integer taskListId) {
        taskService.deleteTaskList(taskListId);
        return "redirect:/get-lists";
    }

    @PostMapping("/add-task")
    public String addTask(@ModelAttribute("task") Task task) {
        taskService.addTask(task);
        return "redirect:/get-lists";
    }

    @PostMapping("/add-list")
    public String addTaskList(@ModelAttribute("taskList") TaskList taskList) {
        taskService.addTaskList(taskList);
        return "redirect:/get-lists";
    }

    @GetMapping(path = {"/get-lists", "/"})
    public String getLists(ModelMap map) {
        prepareModelMap(map, taskService.getAllTaskLists());
        return "index";
    }

    private void prepareModelMap(ModelMap map, List<TaskList> lists) {
        map.addAttribute("task", new Task());
        map.addAttribute("lists", lists);
        map.addAttribute("taskList", new TaskList());
    }
}
