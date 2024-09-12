package com.melloProj.Mello.repositories.project;


import com.melloProj.Mello.models.project.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    //TODO убрать этот метод, потому что нету ссылки на ProjectMember

    Project findById(Integer id);
}
