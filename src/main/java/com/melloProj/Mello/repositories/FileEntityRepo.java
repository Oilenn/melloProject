package com.melloProj.Mello.repositories;

import com.melloProj.Mello.models.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileEntityRepo extends JpaRepository<FileEntity, Long> {
    public List<FileEntity> findFileEntityByFullName(String name);
}
