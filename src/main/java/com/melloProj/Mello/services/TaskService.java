package com.melloProj.Mello.services;

import com.melloProj.Mello.models.Task;
import com.melloProj.Mello.repositories.ListRepository;
import com.melloProj.Mello.repositories.TaskRepository;
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

    public Task getByListId(Long id){
        return taskRepository.findByList(listRepository.findById(id).orElse(null));
    }

    public void deleteTask(Long id){
        Task task = taskRepository.findById(id).orElse(null);;

        if(task != null){
            taskRepository.delete(task);
        }
    }
}
