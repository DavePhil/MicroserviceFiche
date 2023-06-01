package com.foft.microservicefiche.bean;

import com.foft.microserviceuniteenseignement.modele.Semestre;

import lombok.Data;

@Data
public class UniteEnseignementBean {
    private Integer id;
    private String code;
    private int idClasse;
    private Semestre semestre;
}
