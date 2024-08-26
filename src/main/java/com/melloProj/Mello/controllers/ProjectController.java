package com.melloProj.Mello.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.melloProj.Mello.models.List;
import com.melloProj.Mello.models.Project;
import com.melloProj.Mello.models.Task;
import com.melloProj.Mello.services.ListService;
import com.melloProj.Mello.services.ProjectService;
import com.melloProj.Mello.services.TaskService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController("projects")
public class ProjectController {
    @Autowired
    ProjectService projectService;

    @Autowired
    TaskService taskService;

    @Autowired
    ListService listService;

    @SneakyThrows
    @CrossOrigin
    @GetMapping("project/{id}")
    public ResponseEntity<String> getProject(@PathVariable Integer id) {
        return ResponseEntity.ok().body(new ObjectMapper().writeValueAsString(projectService.getProject(id)));
    }
    @SneakyThrows
    @CrossOrigin
    @PostMapping("project")
    public ResponseEntity<String> postProject(@RequestBody Project project) {
        return ResponseEntity.ok().body(new ObjectMapper().writeValueAsString(projectService.createProject(project)));
    }
    @SneakyThrows
    @CrossOrigin
    @DeleteMapping("project")
    public ResponseEntity<String> deleteProject(@RequestBody Project project) {
        return ResponseEntity.ok().body(new ObjectMapper().writeValueAsString(projectService.createProject(project)));
    }


    @SneakyThrows
    @CrossOrigin
    @PostMapping("task/{listId}/{id}")
    public ResponseEntity<String> getTask(@PathVariable Long taskId, @PathVariable Long listId){
        return ResponseEntity.ok().body(new ObjectMapper().writeValueAsString(taskService.getById(taskId)));
    }
    @SneakyThrows
    @CrossOrigin
    @PostMapping("task")
    public ResponseEntity<String> postTask(@RequestBody Task task){
        return ResponseEntity.ok().body(new ObjectMapper().writeValueAsString(taskService.createTask(task)));
    }
}
