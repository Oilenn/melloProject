package com.melloProj.Mello.services.project;

import com.melloProj.Mello.models.project.List;
import com.melloProj.Mello.models.project.Task;
import com.melloProj.Mello.repositories.project.ListRepository;
import com.melloProj.Mello.repositories.project.TaskRepository;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;


@Service
public class ListService {
    private final ListRepository listRepository;
    private final TaskRepository taskRepository;

    public ListService(ListRepository listRepository, TaskRepository taskRepository) {
        this.listRepository = listRepository;
        this.taskRepository = taskRepository;
    }

    @SneakyThrows
    public List getById(@PathVariable Long id){
        return listRepository.findById(id).orElse(null);
    }

    public List createList(List list) {
        return listRepository.save(list);
    }

    public List deleteList(Long id) {
        List list = listRepository.findById(id).orElse(null);;

        if(list != null){
            listRepository.delete(list);
        }

        return list;
    }

    public java.util.List<Task> getTasksByList(List list) {
        return taskRepository.findByList(list.getId());
    }

    public void updateTaskList(Long taskId, Long newList) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new RuntimeException("Task not found"));
        task.setList(newList);
        taskRepository.save(task);
    }
}
