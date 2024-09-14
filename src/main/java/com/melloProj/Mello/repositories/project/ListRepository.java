package com.melloProj.Mello.repositories.project;

import com.melloProj.Mello.models.project.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ListRepository extends JpaRepository<List<C>, Long> {

}
