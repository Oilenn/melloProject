package com.melloProj.Mello.services;

import com.melloProj.Mello.models.Project;
import com.melloProj.Mello.models.Task;
import com.melloProj.Mello.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public class ProjectService {
    @Autowired
    ProjectRepository projectRepository;

    public Project getProject(@PathVariable Integer projectId) {
        return projectRepository.findById(projectId);
    }

    public Project createProject(Project project){
        if (project == null) return null;
        return projectRepository.save(project);
    }

    public void deleteProject(Long id){
        Project task = projectRepository.findById(id).orElse(null);;

        if(task != null){
            projectRepository.delete(task);
        }
    }
}
