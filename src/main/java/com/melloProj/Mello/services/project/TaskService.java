package com.melloProj.Mello.services.project;

import com.melloProj.Mello.models.project.Task;
import com.melloProj.Mello.repositories.project.ListRepository;
import com.melloProj.Mello.repositories.project.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ListRepository listRepository;

    public Task createTask(Task task){
        return taskRepository.save(task);
    }

    public Task getById(Long id){
        return taskRepository.findById(id).orElse(null);
    }

    public Task deleteTask(Long id){
        Task task = taskRepository.findById(id).orElse(null);;

        if(task != null){
            taskRepository.delete(task);
        }

        return task;
    }

    public void updateTaskList(Long taskId, Long newList) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new RuntimeException("Task not found"));
        task.setList(newList);
        taskRepository.save(task);
    }
}
