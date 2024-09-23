package com.melloProj.Mello.services.project;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.melloProj.Mello.models.project.Project;
import com.melloProj.Mello.models.project.ProjectMember;
import com.melloProj.Mello.models.project.UserRights;
import com.melloProj.Mello.models.user.MelloUser;
import com.melloProj.Mello.repositories.project.ProjectRepository;
import com.melloProj.Mello.repositories.project.ProjectMemberRepository;
import com.melloProj.Mello.repositories.system.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProjectService {
    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    ProjectMemberRepository projectMemberRepository;
    @Autowired
    UserRepository userRepository;

    public Project getProject(@PathVariable Long projectId) {
        return projectRepository.findById(projectId).orElse(null);
    }

    public Project createProject(Project project, Long creator){
        if (project == null) return null;

        Project newProject = projectRepository.save(project);

        ProjectMember projectMember = new ProjectMember();
        projectMember.setProject(newProject.getId());
        projectMember.setMelloUser(creator);
        projectMemberRepository.save(projectMember);

        return project;
    }

    public Project deleteProject(Long id){
        Project project = projectRepository.findById(id).orElse(null);;

        if(project != null){
            projectRepository.delete(project);
            return project;
        }
        return null;
    }

    public ProjectMember addMember(Long user, Long projectId){
        ProjectMember member = new ProjectMember();
        member.setMelloUser(user);
        member.setRole("");
        member.setRights(UserRights.Member);
        return projectMemberRepository.save(member);
    }

    public ProjectMember updateMember(ProjectMember member){
        return projectMemberRepository.save(member);
    }

    public ProjectMember deleteMember(Long memberId){
        ProjectMember projectMember = projectMemberRepository.findById(memberId).orElse(null);

        if(projectMember != null){
            projectMemberRepository.delete(projectMember);
            return projectMember;
        }
        return null;
    }

    public List<Project> getProjectsByUser(Long id){
        //TODO расписать многие-ко-многим и исправить проблему
        MelloUser user = userRepository.findById(id).orElse(null);
        List<ProjectMember> projectMembers = projectMemberRepository.findByMelloUser(user.getId());

        List<Project> projects = new ArrayList<Project>();
        for (ProjectMember projectMember: projectMembers) {
            projects.add(projectRepository.findById(projectMember.getProject()).orElse(null));
        }

        return projects;
    }

    public ProjectMember getMember(Long id){
        return projectMemberRepository.findById(id).orElse(null);
    }

    public List<ProjectMember> getUsersByProject(Long projectId){
        return projectMemberRepository.findByProject(projectId);
    }

    public Boolean isUserInProject(Long user, Long projectId){
        List<ProjectMember> members = getUsersByProject(projectId);

        for(ProjectMember member: members){
            if(Objects.equals(member.getMelloUser(), user)){
                return true;
            }
        }

        return false;
    }
}
