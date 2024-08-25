package com.melloProj.Mello.controllers;

import com.melloProj.Mello.models.Project;
import com.melloProj.Mello.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ProjectController {
    @Autowired
    ProjectRepository projectRepository;

    @GetMapping("/project/{projectId}")
    public String showProjectPage(@PathVariable Integer projectId, Model model) {
        System.out.println("Received projectId: " + projectId);
        Project project = projectRepository.findById(projectId).get(); // Используйте сервис для получения проекта по ID
        if (project == null) {
            throw new RuntimeException("Project not found");
        }

        model.addAttribute("project", project);
        return "project";
    }

}
