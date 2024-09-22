package com.melloProj.Mello.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.melloProj.Mello.models.project.List;
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

@RestController("lists")
public class ListController {
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
    @GetMapping("list/{listId}")
    @Operation(summary = "Получить список задач")
    public ResponseEntity<String> getList(@RequestParam("TOKEN") String token,
                                          @PathVariable Long listId){
        MelloUser user = tokenService.getUserByToken(token);
        if(user == null){
            return ResponseEntity.badRequest().body("Error: User is not found");
        }

        return ResponseEntity.ok().body(new ObjectMapper().writeValueAsString(listService.getById(listId)));
    }
    @SneakyThrows
    @CrossOrigin
    @PostMapping("list")
    @Operation(summary = "Создать список задач")
    public ResponseEntity<String> addList(@RequestParam("TOKEN") String token,
                                          @RequestBody List list){
        MelloUser user = tokenService.getUserByToken(token);
        if(user == null){
            return ResponseEntity.badRequest().body("Error: User is not found");
        }

        return ResponseEntity.ok().body(new ObjectMapper().writeValueAsString(listService.createList(list)));
    }
    @SneakyThrows
    @CrossOrigin
    @DeleteMapping("list")
    @Operation(summary = "Удалить список задач")
    public ResponseEntity<String> deleteList(@RequestParam("TOKEN") String token,
                                             @RequestBody Long taskId){
        MelloUser user = tokenService.getUserByToken(token);
        if(user == null){
            return ResponseEntity.badRequest().body("Error: User is not found");
        }

        return ResponseEntity.ok().body(new ObjectMapper().writeValueAsString(listService.deleteList(taskId)));
    }
}
