package com.melloProj.Mello.models.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@Entity
@Table(name = "tokens")
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long melloUser;
    @JsonIgnore
    private OffsetDateTime expTime;

    private String tokenPart;

//    private String publicPart;
//    private String privatePart;
}
