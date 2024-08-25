package com.melloProj.Mello.controllers;

import com.melloProj.Mello.models.List;
import com.melloProj.Mello.models.MelloUser;
import com.melloProj.Mello.models.Project;
import com.melloProj.Mello.repositories.ListRepository;
import com.melloProj.Mello.repositories.ProjectRepository;
import com.melloProj.Mello.repositories.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Date;

@Controller
public class ProjectsPickController {
    @Autowired
    private ListRepository listRepository;
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/projects")
    public String home(Model model) {
        model.addAttribute("projects", projectRepository.findByUser(userRepository.findById(602).get())); // Добавляем их в модель

        return "projects_home"; // Имя вашего шаблона Thymeleaf
    }
}