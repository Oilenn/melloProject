package com.melloProj.Mello.repositories.project;

import com.melloProj.Mello.models.project.List;
import com.melloProj.Mello.models.project.UserProject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProjectRepository extends JpaRepository<UserProject, Long> {

}
