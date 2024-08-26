package com.melloProj.Mello.controllers;

import com.melloProj.Mello.models.List;
import com.melloProj.Mello.models.Task;
import com.melloProj.Mello.services.ListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


//Контроллер для взаимодействия с листами
@RestController("lists")
public class ListController {

    private final ListService listService;

    @Autowired
    public ListController(ListService listService) {
        this.listService = listService;
    }

    @GetMapping("/{listId}/tasks")
    public ResponseEntity<Map<Integer, Task>> getTasksByList(@PathVariable List list) {
        Map<Integer, Task> tasks = listService.getTasksByList(list);
        return ResponseEntity.ok(tasks);
    }

    @PostMapping("/{listId}/tasks/{taskId}")
    public ResponseEntity<Void> moveTask(@PathVariable List listId,
                                         @PathVariable Long taskId,
                                         @RequestBody List newList) {
        listService.updateTaskList(taskId, newList);
        return ResponseEntity.ok().build();
    }
}
