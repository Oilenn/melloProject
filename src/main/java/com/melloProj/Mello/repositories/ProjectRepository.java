package com.melloProj.Mello.repositories;


import com.melloProj.Mello.models.MelloUser;
import com.melloProj.Mello.models.Project;
import org.springframework.data.repository.CrudRepository;

public interface ProjectRepository extends CrudRepository<Project, Integer> {
    Iterable<Project> findByUser(MelloUser user);
}
