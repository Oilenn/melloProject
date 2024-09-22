package com.melloProj.Mello.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.melloProj.Mello.models.project.Project;
import com.melloProj.Mello.models.project.ProjectMember;
import com.melloProj.Mello.models.user.MelloUser;
import com.melloProj.Mello.repositories.project.ProjectMemberRepository;
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

import java.util.Objects;

@RestController("members")
public class ProjectMemberController {
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
    ProjectMemberRepository projectMemberRepository;
    @Autowired
    TokenService tokenService;

    @SneakyThrows
    @CrossOrigin
    @GetMapping("project/users/{projectId}")
    @Operation(summary = "Получить пользователей по проекту")
    public ResponseEntity<String> isUserInProject(@RequestParam("TOKEN") String token,
                                                  @PathVariable Long projectId) {
        MelloUser user = tokenService.getUserByToken(token);
        if(user == null){
            return ResponseEntity.badRequest().body("Error: User is not found");
        }

        if(projectService.isUserInProject(user.getId(), projectId)){
            return ResponseEntity.ok().body(new ObjectMapper().writeValueAsString(true));
        }

        return ResponseEntity.badRequest().body("Error: User doesn't have much rules");
    }

    @SneakyThrows
    @CrossOrigin
    @PostMapping("project/users/{userId}/{projectId}")
    @Operation(summary = "Добавить участника в проект")
    public ResponseEntity<String> addUserInProject(@RequestParam("TOKEN") String token,
                                                   @PathVariable Long userId,
                                                   @PathVariable Long projectId) {
        MelloUser user = tokenService.getUserByToken(token);
        if(user == null){
            return ResponseEntity.badRequest().body("Error: User is not found");
        }

        ProjectMember member = projectService.addMember(userId, projectId);

        return ResponseEntity.ok().body(new ObjectMapper().writeValueAsString(true));
    }

    @SneakyThrows
    @CrossOrigin
    @PutMapping("project/users/{projectId}/{userId}")
    @Operation(summary = "Изменить участника в проекте")
    public ResponseEntity<String> updateMemberInProject(@RequestParam("TOKEN") String token,
                                                        @RequestBody Long member,
                                                        @PathVariable Long projectId) {
        MelloUser user = tokenService.getUserByToken(token);
        if(user == null){
            return ResponseEntity.badRequest().body("Error: User is not found");
        }

        Project project = projectService.getProject(projectId);

        if(!Objects.equals(project.getAdmin(), user.getId())){
            return ResponseEntity.badRequest().body("Error: User doesn't have much rules");
        }


        ProjectMember projectMember = projectMemberRepository.findById(member).orElse(null);


        projectMemberRepository.save(projectMember);

        return ResponseEntity.ok().body(new ObjectMapper().writeValueAsString(true));
    }
}
