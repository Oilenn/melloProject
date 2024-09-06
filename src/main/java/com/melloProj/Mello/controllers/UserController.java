package com.melloProj.Mello.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.melloProj.Mello.models.user.MelloUser;
import com.melloProj.Mello.services.project.ListService;
import com.melloProj.Mello.services.user.MelloUserService;
import com.melloProj.Mello.services.user.TokenService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController("user")
public class UserController {
    @Autowired
    private MelloUserService profileService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private ListService listService;


    @SneakyThrows
    @CrossOrigin
    @GetMapping("profile/{ID}")
    public ResponseEntity<String> getProfile(@PathVariable("ID") Long id, @RequestParam(value = "TOKEN", required = false) String token){
        MelloUser res = profileService.getProfileByID(id);
        if(res.getIsPrivate() && token == null) return ResponseEntity.badRequest().build();
        if(!res.getIsPrivate()) return ResponseEntity.ok(new ObjectMapper().writeValueAsString(res));
        return ResponseEntity.badRequest().body("Error: could not find user");
    }
    @SneakyThrows
    @CrossOrigin
    @GetMapping("profile/self")
    public ResponseEntity<String> getSelf(@RequestParam("TOKEN") String token){
        MelloUser user = tokenService.getUserByToken(token);
        if(user != null) return ResponseEntity.ok().body(new ObjectMapper().writeValueAsString(user));
        return ResponseEntity.badRequest().build();
    }
    @SneakyThrows
    @CrossOrigin
    @PostMapping("profile")
    public ResponseEntity<String> changeSelf(@RequestParam("TOKEN") String token, @RequestBody MelloUser profile){
        MelloUser user = tokenService.getUserByToken(token);
        if(Objects.equals(user.getId(), profile.getId()))
            return ResponseEntity.ok().body(new ObjectMapper().writeValueAsString(profileService.updateProfile(profile)));

        return ResponseEntity.badRequest().build();
    }
}
