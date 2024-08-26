package com.melloProj.Mello.repositories;

import com.melloProj.Mello.models.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface ListRepository extends JpaRepository<List, Long> {
}
