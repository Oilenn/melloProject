package com.melloProj.Mello.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.melloProj.Mello.models.project.List;
import com.melloProj.Mello.models.project.Task;
import com.melloProj.Mello.models.user.Commentary;
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

import java.util.Date;

@RestController("project")
public class InProjectController {
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
    @PostMapping("task")
    @Operation(summary = "Создать задачу")
    public ResponseEntity<String> postTask(@RequestParam("TOKEN") String token,
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

        listService.updateTaskList(taskId, listId);
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
    public ResponseEntity<String> postList(@RequestParam("TOKEN") String token,
                                           @RequestBody List<C> list){
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

    @SneakyThrows
    @CrossOrigin
    @PostMapping("task/{taskId}/commentary/{text}")
    @Operation(summary = "Добавить комментарий")
    public ResponseEntity<String> addCommentary(@RequestParam("TOKEN") String token,
                                                @PathVariable String text,
                                                @PathVariable Long taskId){
        MelloUser user = tokenService.getUserByToken(token);
        if(user == null){
            return ResponseEntity.badRequest().body("Error: User is not found");
        }

        Commentary newCommentary = new Commentary();
        newCommentary.setDate(new Date());
        newCommentary.setUser(user.getId());
        newCommentary.setTask(taskId);
        newCommentary.setText(text);

        return ResponseEntity.ok().body(new ObjectMapper().writeValueAsString(commentaryRepository.save(newCommentary)));
    }

    @SneakyThrows
    @CrossOrigin
    @GetMapping("task/commentary/{commentaryId}")
    @Operation(summary = "Получить комментарий")
    public ResponseEntity<String> getCommentary(@RequestParam("TOKEN") String token,
                                                @PathVariable Long commentaryId){
        MelloUser user = tokenService.getUserByToken(token);
        if(user == null){
            return ResponseEntity.badRequest().body("Error: User is not found");
        }

        return ResponseEntity.ok().body(new ObjectMapper().writeValueAsString(commentaryRepository.findById(commentaryId)));
    }

    @SneakyThrows
    @CrossOrigin
    @GetMapping("task/{taskId}/commentary")
    @Operation(summary = "Получить все комментарии задачи")
    public ResponseEntity<String> getAllCommentaries(@RequestParam("TOKEN") String token,
                                                     @PathVariable Long taskId){
        MelloUser user = tokenService.getUserByToken(token);
        if(user == null){
            return ResponseEntity.badRequest().body("Error: User is not found");
        }

        java.util.List<Commentary> commentaries = commentaryRepository.findAllByTask(taskId);

        return ResponseEntity.ok().body(new ObjectMapper().writeValueAsString(commentaries));
    }
}
