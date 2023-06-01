package com.foft.microservicefiche.bean;



import com.foft.microserviceprogramme.modele.Jour;
import com.foft.microserviceprogramme.modele.Seance;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.time.LocalTime;

@Data
public class ProgrammeBean {

    private Integer id;
    private LocalTime heureDeDebut;
    private LocalTime heureDeFin;
    private String totalHoraire;
    private Integer idUe;
    private Integer idSalle;
    private Integer idEnseignant;
    private Integer idSemestre;
    private Integer idClasse;
    private Jour jour;
    private Seance Seance;
}
