package com.melloProj.Mello.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.melloProj.Mello.models.project.Project;
import com.melloProj.Mello.models.project.Task;
import com.melloProj.Mello.models.system.ReferenceList;
import com.melloProj.Mello.models.user.MelloUser;
import com.melloProj.Mello.services.ReferenceListService;
import com.melloProj.Mello.services.project.ListService;
import com.melloProj.Mello.services.project.ProjectService;
import com.melloProj.Mello.services.project.TaskService;
import com.melloProj.Mello.services.user.MelloUserService;
import com.melloProj.Mello.services.user.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController("projects")
public class ProjectController {
    @Autowired
    ProjectService projectService;
    @Autowired
    TaskService taskService;
    @Autowired
    ListService listService;

    @Autowired
    MelloUserService melloUserService;
    @Autowired
    ReferenceListService referenceListService;
    @Autowired
    TokenService tokenService;


    @CrossOrigin
    @GetMapping("project/{id}")
    @Operation(summary = "Получить проект")
    public ResponseEntity<String> getProject(@RequestParam("TOKEN") String token, @PathVariable Long id) throws JsonProcessingException {
        MelloUser user = tokenService.getUserByToken(token);
        if(user == null){
            return ResponseEntity.badRequest().body("User is not authorizated");
        }



        return ResponseEntity.ok().body(new ObjectMapper().writeValueAsString(projectService.getProject(id)));
    }
    @CrossOrigin
    @PostMapping("project")
    @Operation(summary = "Создать проект")
    public ResponseEntity<String> postProject(@RequestParam("TOKEN") String token, @RequestBody Project project) throws JsonProcessingException {
        MelloUser user = tokenService.getUserByToken(token);
        if(user == null){
            return ResponseEntity.badRequest().body("Error: User is not found");
        }


        Project project1 = projectService.createProject(project, user.getId());


        return ResponseEntity.ok().body(new ObjectMapper().writeValueAsString(project1));
    }
    @SneakyThrows
    @CrossOrigin
    @DeleteMapping("project")
    @Operation(summary = "Удалить проект")
    public ResponseEntity<String> deleteProject(@RequestParam("TOKEN") String token, @RequestBody Project project) {
        MelloUser user = tokenService.getUserByToken(token);
        if(user == null){
            return ResponseEntity.badRequest().body("Error: User is not found");
        }

        if(!Objects.equals(project.getAdmin(), user.getId())){
            return ResponseEntity.badRequest().body("Error: User doesn't have much rule");
        }


        return ResponseEntity.ok().body(new ObjectMapper().writeValueAsString(projectService.deleteProject(project.getId())));
    }


    @SneakyThrows
    @CrossOrigin
    @PostMapping("task/{listId}/{id}")
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
    @PostMapping("projects")
    @Operation(summary = "Получить проекты по пользователю")
    public ResponseEntity<String> getProjectByUser(@RequestParam("TOKEN") String token) {
        MelloUser user = tokenService.getUserByToken(token);
        if(user == null){
            return ResponseEntity.badRequest().body("Error: User is not found");
        }

        List<Project> projects = projectService.getProjectsByUser(user.getId());

        return ResponseEntity.ok().body(new ObjectMapper().writeValueAsString(projects));
    }

    @SneakyThrows
    @CrossOrigin
    @PostMapping("project/users/{id}")
    @Operation(summary = "Получить пользователей по проекту")
    public ResponseEntity<String> getUsersByProject(@RequestParam("TOKEN") String token,
                                                    @PathVariable Long projectId) {
        MelloUser user = tokenService.getUserByToken(token);
        if(user == null){
            return ResponseEntity.badRequest().body("Error: User is not found");
        }

        List<Project> projects = projectService.getProjectsByUser(user.getId());
        if(projects == null){
            return ResponseEntity.badRequest().body("Error: User doesn't have much rule");
        }


        return ResponseEntity.ok().body(new ObjectMapper().writeValueAsString(projects));
    }
}
