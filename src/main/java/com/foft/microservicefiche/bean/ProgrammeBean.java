package com.foft.microservicefiche.bean;



import com.foft.microserviceprogramme.modele.Jour;
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
    private Jour jour;
}
