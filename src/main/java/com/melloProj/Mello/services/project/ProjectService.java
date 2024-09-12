package com.melloProj.Mello.services.project;

import com.melloProj.Mello.models.project.Project;
import com.melloProj.Mello.models.project.ProjectMember;
import com.melloProj.Mello.repositories.project.ProjectRepository;
import com.melloProj.Mello.repositories.project.UserProjectRepository;
import com.melloProj.Mello.repositories.system.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public class ProjectService {
    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    UserProjectRepository userProjectRepository;
    @Autowired
    UserRepository userRepository;

    public Project getProject(@PathVariable Long projectId) {
        return projectRepository.findById(projectId).orElse(null);
    }

    public Project createProject(Project project, Long creator){
        if (project == null) return null;
        return projectRepository.save(project);
    }

    public Project deleteProject(Long id){
        Project project = projectRepository.findById(id).orElse(null);;

        if(project != null){
            projectRepository.delete(project);
            return project;
        }
        return null;
    }

    public List<Project> getProjectsByUser(Long id){
        //TODO расписать многие-ко-многим и исправить проблему
        return projectRepository.findByMelloUsers(id);
    }

    public List<ProjectMember> getUsersByProject(Long projectId){
        //TODO сделать репозиторий мемберов и получать их по проекту
    }
}
