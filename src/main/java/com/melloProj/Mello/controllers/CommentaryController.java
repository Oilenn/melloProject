package com.melloProj.Mello.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.Objects;

@RestController("commentary")
public class CommentaryController {
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

    //Немного коряво - бизнес логику лучше перенести в сервис
    //TODO: переместить всё в сервис

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
        newCommentary.setMelloUser(user.getId());
        newCommentary.setTask(taskId);
        newCommentary.setText(text);

        return ResponseEntity.ok().body(new ObjectMapper().writeValueAsString(commentaryRepository.save(newCommentary)));
    }

    @SneakyThrows
    @CrossOrigin
    @PutMapping("task")
    @Operation(summary = "Обновить комментарий")
    public ResponseEntity<String> updateCommentary(@RequestParam("TOKEN") String token,
                                                   @RequestBody Commentary commentary){
        MelloUser user = tokenService.getUserByToken(token);
        if(user == null){
            return ResponseEntity.badRequest().body("Error: User is not found");
        }

        return ResponseEntity.ok().body(new ObjectMapper().writeValueAsString(commentaryRepository.save(commentary)));
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
    @DeleteMapping("task/commentary/{commentaryId}")
    @Operation(summary = "Удалить комментарий")
    public ResponseEntity<String> deleteCommentary(@RequestParam("TOKEN") String token,
                                                @PathVariable Long commentaryId){
        MelloUser user = tokenService.getUserByToken(token);
        if(user == null){
            return ResponseEntity.badRequest().body("Error: User is not found");
        }
        Commentary commentary = commentaryRepository.findById(commentaryId).orElse(null);
        if(commentary == null){
            return ResponseEntity.badRequest().body("Error: Commentary is not found");
        }

        if(Objects.equals(commentary.getMelloUser(), user.getId())){
            return ResponseEntity.ok().body(new ObjectMapper().writeValueAsString(commentaryRepository.findById(commentaryId)));
        }

        return ResponseEntity.badRequest().body("Error: User is not an author of commentary");
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
