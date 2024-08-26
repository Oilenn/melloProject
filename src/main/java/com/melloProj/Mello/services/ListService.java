package com.melloProj.Mello.services;

import com.melloProj.Mello.models.List;
import com.melloProj.Mello.models.Task;
import com.melloProj.Mello.repositories.ListRepository;
import com.melloProj.Mello.repositories.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;



@Service
public class ListService {
    private final ListRepository listRepository;
    private final TaskRepository taskRepository;

    public ListService(ListRepository listRepository, TaskRepository taskRepository) {
        this.listRepository = listRepository;
        this.taskRepository = taskRepository;
    }

    public Map<Integer, Task> getTasksByList(List list) {
        java.util.List<Task> tasks = (java.util.List<Task>) taskRepository.findByList(list);
        Map<Integer, Task> taskMap = new HashMap<>();
        for (int i = 0; i < tasks.size(); i++) {
            taskMap.put(i + 1, tasks.get(i));
        }
        return taskMap;
    }

    public void updateTaskList(Long taskId, List newList) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new RuntimeException("Task not found"));
        task.setList(newList);
        taskRepository.save(task);
    }
}
