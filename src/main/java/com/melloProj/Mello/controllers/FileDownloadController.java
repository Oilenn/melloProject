package com.melloProj.Mello.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.melloProj.Mello.models.system.FileEntity;
import com.melloProj.Mello.services.FileEntityService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Paths;

@RestController("fileDownload")
public class FileDownloadController {
    private final Environment env;
    private final FileEntityService fileEntityService;
    @Autowired
    public FileDownloadController(Environment env, FileEntityService fileEntityService){
        this.env = env;
        this.fileEntityService = fileEntityService;
    }
    @CrossOrigin
    @GetMapping("uploads/{ID}")
    public ResponseEntity<InputStreamResource> download(@PathVariable("ID") Long ID){
        HttpHeaders headers = new HttpHeaders();
        FileEntity fileEntity = fileEntityService.getFileEntityByID(ID);
        String originalFileName = fileEntity.getOriginalName();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+originalFileName);

        File file = new File(Paths.get(env.getProperty("app.file.upload-dir", "./uploads/files"))
                .toAbsolutePath().normalize() + "/" + fileEntity.getFullName());

        InputStreamResource resource = null;
        try {
            resource = new InputStreamResource(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Error: file " + ID + "not found!", new IllegalArgumentException(e));
        }

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(resource);
    }
    @SneakyThrows
    @CrossOrigin
    @GetMapping("info/{ID}")
    public ResponseEntity<String> getInfo(@PathVariable("ID") Long id){
        if(id == null) return ResponseEntity.badRequest().build();
        FileEntity file = fileEntityService.getFileEntityByID(id);
        if(file != null) return ResponseEntity.ok().body(new ObjectMapper().writeValueAsString(file));
        return ResponseEntity.badRequest().build();
    }
}
