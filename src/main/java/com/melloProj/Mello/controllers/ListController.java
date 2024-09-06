package com.melloProj.Mello.controllers;

import org.springframework.web.bind.annotation.*;


//Контроллер для взаимодействия с листами
@RestController("lists")
public class ListController {
//    @Autowired
//    private ListService listService;
//
//    @SneakyThrows
//    @GetMapping("/{listId}/tasks")
//    @Operation(summary = "Получить задачи по списку")
//    public ResponseEntity<String> getTasksByList(@PathVariable List list) {
//        java.util.List<Task> tasks = listService.getTasksByList(list);
//        return ResponseEntity.ok().body(new ObjectMapper().writeValueAsString(listService.getTasksByList(list)));
//    }
//
//    @SneakyThrows
//    @PostMapping("/{listId}/tasks/{taskId}")
//    @Operation(summary = "Переместить задачу в новый список")
//    public ResponseEntity<Void> moveTask(@PathVariable List listId,
//                                         @PathVariable Long taskId,
//                                         @RequestBody List newList) {
//        listService.updateTaskList(taskId, newList);
//        return ResponseEntity.ok().build();
//    }
}
