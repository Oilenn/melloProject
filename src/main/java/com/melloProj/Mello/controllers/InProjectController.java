package com.melloProj.Mello.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.melloProj.Mello.models.project.List;
import com.melloProj.Mello.models.project.Task;
import com.melloProj.Mello.services.project.ListService;
import com.melloProj.Mello.services.project.ProjectService;
import com.melloProj.Mello.services.project.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("project")
public class InProjectController {
    @Autowired
    ProjectService projectService;

    @Autowired
    TaskService taskService;

    @Autowired
    ListService listService;

    @SneakyThrows
    @CrossOrigin
    @PostMapping("task")
    @Operation(summary = "Создать задачу")
    public ResponseEntity<String> postTask(@RequestParam("TOKEN") String token,
                                           @RequestBody Task task){
        return ResponseEntity.ok().body(new ObjectMapper().writeValueAsString(taskService.createTask(task)));
    }
    @SneakyThrows
    @CrossOrigin
    @DeleteMapping("task")
    @Operation(summary = "Удалить задачу")
    public ResponseEntity<String> deleteTask(@RequestParam("TOKEN") String token,
                                             @RequestBody Task task){
        return ResponseEntity.ok().body(new ObjectMapper().writeValueAsString(taskService.deleteTask(task.getId())));
    }
    @SneakyThrows
    @PostMapping("/{listId}/tasks/{taskId}")
    @Operation(summary = "Переместить задачу в новый список")
    public ResponseEntity<Void> moveTask(@PathVariable List listId,
                                         @PathVariable Long taskId,
                                         @RequestBody List newList) {
        listService.updateTaskList(taskId, newList);
        return ResponseEntity.ok().build();
    }

    @SneakyThrows
    @CrossOrigin
    @PostMapping("tasks/{listId}")
    @Operation(summary = "Получить все задачи")
    public ResponseEntity<String> getTasks(@RequestParam("TOKEN") String token,
                                          @PathVariable Long listId){
        return ResponseEntity.ok().body(new ObjectMapper().writeValueAsString(listService.getTasksByList(listService.getById(listId))));
    }

    @SneakyThrows
    @CrossOrigin
    @PostMapping("list/{listId}")
    @Operation(summary = "Получить список задач")
    public ResponseEntity<String> getList(@RequestParam("TOKEN") String token,
                                          @PathVariable Long listId){
        return ResponseEntity.ok().body(new ObjectMapper().writeValueAsString(listService.getById(listId)));
    }
    @SneakyThrows
    @CrossOrigin
    @PostMapping("list")
    @Operation(summary = "Создать список задач")
    public ResponseEntity<String> postList(@RequestParam("TOKEN") String token,
                                           @RequestBody List list){
        return ResponseEntity.ok().body(new ObjectMapper().writeValueAsString(listService.createList(list)));
    }
    @SneakyThrows
    @CrossOrigin
    @DeleteMapping("list")
    @Operation(summary = "Удалить список задач")
    public ResponseEntity<String> deleteList(@RequestParam("TOKEN") String token,
                                             @RequestBody Task task){
        return ResponseEntity.ok().body(new ObjectMapper().writeValueAsString(listService.deleteList(task.getId())));
    }
}
