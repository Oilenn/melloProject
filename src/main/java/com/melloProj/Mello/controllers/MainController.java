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
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Date;

@Controller
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

    @GetMapping("/")
    public String home(Model model) {
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
        project.setPrivacy("Закрытый");
        project.setUser(user);

        projectRepository.save(project);

        List list = new List();
        list.setName("Задача");
        list.setProject(project);
        listRepository.save(list);

        Iterable<Project> projects = projectRepository.findAll(); // Получаем все проекты
        model.addAttribute("projects", projects); // Добавляем их в модель
        return "home"; // Имя вашего шаблона Thymeleaf
    }
}