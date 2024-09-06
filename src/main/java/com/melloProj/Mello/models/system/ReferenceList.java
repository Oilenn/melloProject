package com.melloProj.Mello.models.system;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "ref_list")
public class ReferenceList {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long next;
    private Long prev;
    private String contentType;
    private Long contentID;
}
