package com.melloProj.Mello.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Commentary implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
//    @ManyToOne
    private MelloUser participant;
    private String text;
    private Date date;
    private Task task;
    //Код(Pk)
    //Участник(Fk)
    //Текст
    //Дата
}
