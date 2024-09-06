package com.melloProj.Mello.repositories.project;

import com.melloProj.Mello.models.project.List;
import com.melloProj.Mello.models.project.Task;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TaskRepository extends JpaRepository<Task, Long> {
    java.util.List<Task> findByList(Long list);
}
