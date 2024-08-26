package com.melloProj.Mello.repositories;


import com.melloProj.Mello.models.MelloUser;
import com.melloProj.Mello.models.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    Iterable<Project> findByUser(MelloUser user);

    Project findById(Integer id);
}
