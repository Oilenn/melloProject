package com.melloProj.Mello.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.melloProj.Mello.models.project.List;
import com.melloProj.Mello.models.user.MelloUser;
import com.melloProj.Mello.models.project.Project;
import com.melloProj.Mello.repositories.project.ListRepository;
import com.melloProj.Mello.repositories.project.ProjectRepository;
import com.melloProj.Mello.repositories.system.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController("main")
public class MainController {
    @Autowired
    private ListRepository listRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private UserRepository userRepository;
    @PersistenceContext
    private EntityManager entityManager;

    public void deleteAllProjects() {
        entityManager.createNativeQuery("DROP TABLE IF EXISTS list").executeUpdate();
        entityManager.createNativeQuery("DROP TABLE IF EXISTS melloUser").executeUpdate();
        entityManager.createNativeQuery("DROP TABLE IF EXISTS project").executeUpdate();
    }

    @PostMapping("/")
    public ResponseEntity<String> home(Model model) throws JsonProcessingException {
        MelloUser user = new MelloUser();
        user.setLogin("1");
        user.setMail("ds");
        user.setPassword("1");
        user.setNickname("nick");
        userRepository.save(user);

        System.out.println(user.getId());

        Project project = new Project();
        project.setName("Проект");
        project.setDateCreation(new Date());
        project.setTheme("Закрытый");
        project.setMelloUsers(user.getId());

        System.out.println(project.getId());

        projectRepository.save(project);

        List list = new List();
        list.setName("Задача");
        list.setListProjectCon(project.getId());
        listRepository.save(list);

        Iterable<Project> projects = projectRepository.findAll(); // Получаем все проекты
        model.addAttribute("projects", projects); // Добавляем их в модель

        System.out.println(project.getId());

        return ResponseEntity.ok().body(new ObjectMapper().writeValueAsString(project));
    }
}