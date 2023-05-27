package com.foft.microservicefiche.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import java.util.Date;

@Data
@Entity
@DynamicUpdate
public class Fiche {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String titre;
    private Date date;
//    private String contenu;
    private String motif;
    private String signatureDelegue;
    private String signatureEnseignant;
    private Integer idProgramme;

    private int state; // -1 => initiés 0 => en cours de validation; 1 =>  rejetés; 2 => validés;  3 => Rattrapage

    public Fiche() {
        super();
        state=-1;
    }


}
