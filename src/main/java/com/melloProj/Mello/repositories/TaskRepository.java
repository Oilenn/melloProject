package com.melloProj.Mello.repositories;

import com.melloProj.Mello.models.List;
import com.melloProj.Mello.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;


public interface TaskRepository extends JpaRepository<Task, Long> {
    Task findByList(List list);
}
