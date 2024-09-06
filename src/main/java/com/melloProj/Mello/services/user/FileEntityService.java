package com.melloProj.Mello.services.user;

import com.melloProj.Mello.models.system.FileEntity;
import com.melloProj.Mello.repositories.system.FileEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.Random;

@Service
public class FileEntityService {
    @Autowired
    private FileEntityRepository fileEntityRepository;
    private final Path fileStorageLocation;

    @Autowired
    public FileEntityService(Environment env) {
        this.fileStorageLocation = Paths.get(env.getProperty("app.file.upload-dir", "./uploads/files"))
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new RuntimeException(
                    "Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    public FileEntity storeFile(MultipartFile file) {

        // Normalize file name
        Random randomNumber = new Random();
        String fileName = new Date().getTime() + (randomNumber.nextInt(900) + 100) + "-file";
            while(fileEntityRepository.findFileEntityByFullName(fileName).size() != 0) fileName = new Date().getTime() + (randomNumber.nextInt(900) + 100) + "-file";

        try {
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            FileEntity fileEntity = this.setFile(file.getOriginalFilename(), fileName);

            return fileEntity;
        } catch (IOException ex) {
            throw new RuntimeException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }
    public FileEntity setFile(String oldFileName, String fullName){
        FileEntity fileEntity = new FileEntity();
        fileEntity.setOriginalName(oldFileName);
        fileEntity.setFullName(fullName);
        //file.setPath(path);
        return fileEntityRepository.save(fileEntity);
    }
    public FileEntity getFileEntityByFullName(String name){
        var res = fileEntityRepository.findFileEntityByFullName(name);
        if(res.size() != 0) return res.get(0);
        return null;
    }
    public FileEntity getFileEntityByID(Long ID){
        var res = fileEntityRepository.findById(ID);
        return res.orElse(null);
    }
}