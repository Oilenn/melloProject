package com.melloProj.Mello.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.melloProj.Mello.models.project.Task;
import com.melloProj.Mello.models.user.MelloUser;
import com.melloProj.Mello.repositories.project.CommentaryRepository;
import com.melloProj.Mello.services.project.ListService;
import com.melloProj.Mello.services.project.ProjectService;
import com.melloProj.Mello.services.project.TaskService;
import com.melloProj.Mello.services.user.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("tasks")
public class TaskController {
    @Autowired
    ProjectService projectService;

    @Autowired
    TaskService taskService;

    @Autowired
    ListService listService;

    @Autowired
    CommentaryRepository commentaryRepository;

    @Autowired
    TokenService tokenService;

    @SneakyThrows
    @CrossOrigin
    @GetMapping("task/{listId}/{id}")
    @Operation(summary = "Получить задачу")
    public ResponseEntity<String> getTask(@RequestParam("TOKEN") String token,
                                          @PathVariable Long taskId,
                                          @PathVariable Long listId) {
        MelloUser user = tokenService.getUserByToken(token);
        if(user == null){
            return ResponseEntity.badRequest().body("Error: User is not found");
        }

        Task task = taskService.getById(taskId);
        if (task != null) {
            return ResponseEntity.ok().body(new ObjectMapper().writeValueAsString(task));
        }
        return ResponseEntity.badRequest().body("Error: couldn't find content!");
    }
    @SneakyThrows
    @CrossOrigin
    @PostMapping("task")
    @Operation(summary = "Создать задачу")
    public ResponseEntity<String> addTask(@RequestParam("TOKEN") String token,
                                          @RequestBody Task task){
        MelloUser user = tokenService.getUserByToken(token);
        if(user == null){
            return ResponseEntity.badRequest().body("Error: User is not found");
        }

        return ResponseEntity.ok().body(new ObjectMapper().writeValueAsString(taskService.createTask(task)));
    }
    @SneakyThrows
    @CrossOrigin
    @DeleteMapping("task")
    @Operation(summary = "Удалить задачу")
    public ResponseEntity<String> deleteTask(@RequestParam("TOKEN") String token,
                                             @RequestBody Task task){
        MelloUser user = tokenService.getUserByToken(token);
        if(user == null){
            return ResponseEntity.badRequest().body("Error: User is not found");
        }

        return ResponseEntity.ok().body(new ObjectMapper().writeValueAsString(taskService.deleteTask(task.getId())));
    }
    @SneakyThrows
    @PutMapping("/{listId}/tasks/{taskId}")
    @Operation(summary = "Переместить задачу в новый список")
    public ResponseEntity<String> moveTask(@RequestParam("TOKEN") String token,
                                           @PathVariable Long listId,
                                           @PathVariable Long taskId) {
        MelloUser user = tokenService.getUserByToken(token);
        if(user == null){
            return ResponseEntity.badRequest().body("Error: User is not found");
        }

        taskService.updateTaskList(taskId, listId);
        return ResponseEntity.ok().build();
    }

    @SneakyThrows
    @CrossOrigin
    @GetMapping("tasks/{listId}")
    @Operation(summary = "Получить все задачи")
    public ResponseEntity<String> getTasks(@RequestParam("TOKEN") String token,
                                           @PathVariable Long listId){
        MelloUser user = tokenService.getUserByToken(token);
        if(user == null){
            return ResponseEntity.badRequest().body("Error: User is not found");
        }

        return ResponseEntity.ok().body(new ObjectMapper().writeValueAsString(listService.getTasksByList(listService.getById(listId))));
    }
}
