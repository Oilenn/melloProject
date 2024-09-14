package com.melloProj.Mello.repositories.project;

import com.melloProj.Mello.models.user.Commentary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentaryRepository extends JpaRepository<Commentary, Long> {
    public List<Commentary> findAllByTask(Long taskId);
}