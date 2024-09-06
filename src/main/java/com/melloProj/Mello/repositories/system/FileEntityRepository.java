package com.melloProj.Mello.repositories.system;

import com.melloProj.Mello.models.system.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileEntityRepository extends JpaRepository<FileEntity, Long> {
    public List<FileEntity> findFileEntityByFullName(String name);
}
