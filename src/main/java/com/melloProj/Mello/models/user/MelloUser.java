package com.melloProj.Mello.models.user;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MelloUser implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nickname;
    private String login;
    private String password;
    private String mail;

    private Boolean isDeleted = false;
    private Boolean isPrivate = false;

    private Long profilePic;
}
