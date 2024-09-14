package com.melloProj.Mello.repositories.project;


import com.melloProj.Mello.models.project.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    Project findById(Integer id);
}
