package com.melloProj.Mello.repositories.project;

import com.melloProj.Mello.models.project.ProjectMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectMemberRepository extends JpaRepository<ProjectMember, Long> {
    public List<ProjectMember> findByProject(Long projectId);
    public List<ProjectMember> findByMelloUser(Long userId);
}
