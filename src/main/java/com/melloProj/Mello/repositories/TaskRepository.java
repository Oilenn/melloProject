package com.melloProj.Mello.repositories;

import com.melloProj.Mello.models.List;
import com.melloProj.Mello.models.Task;
import org.springframework.data.repository.CrudRepository;


public interface TaskRepository extends CrudRepository<Task, Integer> {
    Iterable<Task> findByList(List list);
}
