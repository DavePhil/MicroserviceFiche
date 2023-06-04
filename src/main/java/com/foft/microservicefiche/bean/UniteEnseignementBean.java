package com.foft.microservicefiche.bean;


import lombok.Data;

@Data
public class UniteEnseignementBean {
    private Integer id;
    private String code;
    private int idClasse;
    private SeanceBean semestre;
}
