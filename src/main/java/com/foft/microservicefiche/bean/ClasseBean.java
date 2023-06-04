package com.foft.microservicefiche.bean;

import lombok.Data;

@Data
public class ClasseBean {

    private Integer id;
    private String nom;
    private NiveauBean niveau;
    private SpecialiteBean specialite;
}
