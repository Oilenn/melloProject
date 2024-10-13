package com.melloProj.Mello.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.melloProj.Mello.models.user.MelloUser;
import com.melloProj.Mello.repositories.project.ListRepository;
import com.melloProj.Mello.repositories.project.ProjectRepository;
import com.melloProj.Mello.repositories.system.UserRepository;
import com.melloProj.Mello.services.user.TokenService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller("main")
public class ViewsController {
    @Autowired
    private ListRepository listRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private UserRepository userRepository;
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    TokenService tokenService;
    public void deleteAllProjects() {
        entityManager.createNativeQuery("DROP TABLE IF EXISTS list").executeUpdate();
        entityManager.createNativeQuery("DROP TABLE IF EXISTS melloUser").executeUpdate();
        entityManager.createNativeQuery("DROP TABLE IF EXISTS project").executeUpdate();
    }

    @GetMapping("/")
    public String auth(Model model) throws JsonProcessingException {
//        MelloUser user = new MelloUser();
//        user.setLogin("1");
//        user.setMail("ds");
//        user.setPassword("1");
//        user.setNickname("nick");
//        userRepository.save(user);
//
//        System.out.println(user.getId());
//
//        Project project = new Project();
//        project.setName("Проект");
//        project.setDateCreation(new Date());
//        project.setTheme("Закрытый");
//        project.setAdmin(user.getId());
//
//        System.out.println(project.getId());
//
//        projectRepository.save(project);
//
//        List list = new List();
//        list.setName("Задача");
//        list.setListProjectCon(project.getId());
//        listRepository.save(list);
//
//        Iterable<Project> projects = projectRepository.findAll(); // Получаем все проекты
//        model.addAttribute("projects", projects); // Добавляем их в модель
//
//        System.out.println(project.getId());

        model.addAttribute("message", "Welcome to Thymeleaf!");
        return "auth";
    }

    @GetMapping("/home")
    public String home(@RequestParam("TOKEN") String token, Model model) throws JsonProcessingException {
        Boolean isTokenExist = tokenService.tokenUtils.verifyToken(token);
        System.out.println(isTokenExist);
        if(!isTokenExist){
            return "auth";
        }
        return "home";
    }

    @GetMapping("/register")
    public String register(Model model) throws JsonProcessingException {



        model.addAttribute("message", "Welcome to Thymeleaf!");
        return "register";
    }
}