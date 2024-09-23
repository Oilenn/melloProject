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
    public ResponseEntity<String> getUsersByProject(@RequestParam("TOKEN") String token,
                                                  @PathVariable Long projectId) {
        MelloUser user = tokenService.getUserByToken(token);
        if(user == null){
            return ResponseEntity.badRequest().body("Error: User is not found");
        }

        if(projectService.isUserInProject(user.getId(), projectId)){
            return ResponseEntity.ok().body(new ObjectMapper().writeValueAsString(
                    projectService.getUsersByProject(user.getId())));
        }

        return ResponseEntity.badRequest().body("Error: User doesn't have much rules");
    }

    @SneakyThrows
    @CrossOrigin
    @PostMapping("project/members/{userId}/{projectId}")
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
    @PutMapping("project/members")
    @Operation(summary = "Изменить участника в проекте")
    public ResponseEntity<String> updateMemberInProject(@RequestParam("TOKEN") String token,
                                                        @RequestBody ProjectMember member) {
        MelloUser user = tokenService.getUserByToken(token);
        if(user == null){
            return ResponseEntity.badRequest().body("Error: User is not found");
        }

        Project project = projectService.getProject(member.getProject());

        if(!Objects.equals(project.getAdmin(), user.getId())){
            return ResponseEntity.badRequest().body("Error: User doesn't have much rules");
        }

        return ResponseEntity.ok().body(new ObjectMapper().writeValueAsString(projectService.updateMember(member)));
    }

    @SneakyThrows
    @CrossOrigin
    @DeleteMapping("project/members/{memberId}")
    @Operation(summary = "Удалить участника из проекта")
    public ResponseEntity<String> deleteProjectMember(@RequestParam("TOKEN") String token,
                                                        @PathVariable Long memberId) {
        MelloUser user = tokenService.getUserByToken(token);
        if(user == null){
            return ResponseEntity.badRequest().body("Error: User is not found");
        }

        ProjectMember projectMember = projectMemberRepository.findById(memberId).orElse(null);

        assert projectMember != null;
        Project project = projectService.getProject(projectMember.getProject());

        if(!Objects.equals(project.getAdmin(), user.getId())){
            return ResponseEntity.badRequest().body("Error: User doesn't have much rules");
        }

        return ResponseEntity.ok().body(new ObjectMapper().writeValueAsString(
                projectService.deleteProject(user.getId())));
    }
}
